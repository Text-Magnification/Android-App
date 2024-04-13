package com.example.textmag.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.graphics.Paint
import android.graphics.Rect
import android.view.View
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.FrameLayout
import android.widget.LinearLayout
import androidx.camera.mlkit.vision.MlKitAnalyzer
import androidx.camera.view.CameraController.COORDINATE_SYSTEM_VIEW_REFERENCED
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions

var buffer = 0


fun bindPreview(
    onTextRecognition: (String, List<Rect>, List<Float>) -> Unit,
    cameraProvider: LifecycleCameraController,
    arEnabled: Boolean,
    lifecycleOwner: LifecycleOwner,
    context: Context
): LifecycleCameraController {
    val textRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    cameraProvider.setImageAnalysisAnalyzer(
        ContextCompat.getMainExecutor(context),
        MlKitAnalyzer(
            listOf(textRecognizer),
            COORDINATE_SYSTEM_VIEW_REFERENCED,
            ContextCompat.getMainExecutor(context)
        ) { result: MlKitAnalyzer.Result? ->
            val text = result?.getValue(textRecognizer)?.text ?: ""
            if (text == "") {
                buffer = 0
                return@MlKitAnalyzer
            }
            val blocks = result?.getValue(textRecognizer)?.textBlocks ?: emptyList()
            val boundingBoxes = mutableListOf<Rect>()
            val angles = mutableListOf<Float>()

            if (arEnabled) {
                blocks.forEach { block ->
                    val lines = block.lines
                    lines.forEach { line ->
                        line.boundingBox?.let {
                            boundingBoxes.add(it)
                            angles.add(line.angle)
                        }
                    }
                }
            }

            if (buffer == 0) {
                onTextRecognition(text, boundingBoxes, angles)
                ++buffer
            }
            else {
                buffer = (buffer + 1) % 50
            }
            return@MlKitAnalyzer
        }
    )
    cameraProvider.bindToLifecycle(lifecycleOwner)
    return cameraProvider
}

class BoundingBoxOverlay(context : Context) : View(context) {
    private var boundingBoxes: List<Rect> = emptyList()
    private var angles: List<Float> = emptyList()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply {
            color =  argb(100, 0, 0, 0)
            style = Paint.Style.FILL
        }

        boundingBoxes.zip(angles).forEach { pair ->
            // Rotate canvas to orientation of line before drawing
            // Correct skew by modifying angle
            val angle = if (pair.second <= 90F) pair.second + 90 else pair.second - 270
            canvas.save()
            canvas.rotate(angle)
            canvas.drawRect(pair.first, paint)
            canvas.restore()
        }
    }

    fun updateBoundingBoxes(boxes: List<Rect>, angs: List<Float>, arEnabled: Boolean) {
        if (arEnabled) {
            boundingBoxes = boxes
            angles = angs
        }
        else {
            boundingBoxes = emptyList()
            angles = emptyList()
        }
        invalidate() // Redraw the view with updated bounding boxes
    }
}

@Composable
fun CameraPreview(
    cameraProvider: LifecycleCameraController,
    onTextRecognition: (String, List<Rect>, List<Float>) -> Unit,
    arEnabled: Boolean
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val boundingBoxOverlay = remember { BoundingBoxOverlay(context) }

    AndroidView(
        factory = { context ->
            val overlayContainer = LinearLayout(context).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                orientation = LinearLayout.VERTICAL

                val previewView = PreviewView(context).apply {
                    setBackgroundColor(rgb(234, 224, 231))
                    layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, 0).apply {
                        weight = 1f // Take remaining vertical space
                    }
                    scaleType = PreviewView.ScaleType.FILL_START
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    controller = bindPreview(
                        { text, boxes, angles ->
                            boundingBoxOverlay.updateBoundingBoxes(boxes, angles, arEnabled)
                            onTextRecognition(text, boxes, angles)
                        },
                        cameraProvider,
                        arEnabled,
                        lifecycleOwner,
                        context
                    )
                }
                addView(previewView)
            }

            FrameLayout(context).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                addView(overlayContainer)
                addView(boundingBoxOverlay)
            }
        }
    )
}