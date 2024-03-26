package com.example.textmag.ui.components

import android.content.Context
import android.graphics.Canvas
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
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.latin.TextRecognizerOptions


fun bindPreview(
    onTextRecognition: (String, List<Rect>, List<Float>) -> Unit,
    cameraProvider: LifecycleCameraController,
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
            val blocks = result?.getValue(textRecognizer)?.textBlocks ?: emptyList()
            val extractedText = StringBuilder()
            val boundingBoxes = mutableListOf<Rect>()
            val angles = mutableListOf<Float>()

            blocks.forEach { block ->
                val lines = block.lines
                lines.forEach {
                    line ->
                        extractedText.append(line.text).append('\n')
                        line.boundingBox?.let {
                            boundingBoxes.add(it)
                            angles.add(line.angle)
                        }
                }
            }

            onTextRecognition(extractedText.toString(), boundingBoxes, angles)
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
            color =  Color(0x00000033).toArgb()
            style = Paint.Style.FILL
        }

        boundingBoxes.forEach { box ->
            // Rotate canvas to orientation of line before drawing
            //canvas.save()
            //canvas.rotate(pair.second)
            canvas.drawRect(box, paint)
            //canvas.restore()
        }
    }

    fun updateBoundingBoxes(boxes: List<Rect>, angs: List<Float>) {
        boundingBoxes = boxes
        angles = angs
        invalidate() // Redraw the view with updated bounding boxes
    }
}

@Composable
fun CameraPreview(
    cameraProvider: LifecycleCameraController,
    onTextRecognition: (String, List<Rect>, List<Float>) -> Unit
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
                            boundingBoxOverlay.updateBoundingBoxes(boxes, angles)
                            onTextRecognition(text, boxes, angles)
                        },
                        cameraProvider,
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