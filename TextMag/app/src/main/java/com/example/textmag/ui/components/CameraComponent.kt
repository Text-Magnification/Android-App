package com.example.textmag.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.graphics.Paint
import android.graphics.Path
import android.graphics.Matrix
import android.graphics.RectF
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
    onTextRecognition: (String, List<Path>) -> Unit,
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
            val boundingBoxes = mutableListOf<Path>()

            if (arEnabled) {
                blocks.forEach { block ->
                    val path = Path()
                    path.moveTo((block.cornerPoints?.get(0)?.x ?: 0).toFloat(), (block.cornerPoints?.get(0)?.y ?: 0).toFloat())
                    for (i in 1..3) {
                        path.lineTo((block.cornerPoints?.get(i)?.x ?: 0).toFloat(), (block.cornerPoints?.get(i)?.y ?: 0).toFloat())
                    }
                    boundingBoxes.add(path)
                }
            }

            if (buffer == 0) {
                onTextRecognition(text, boundingBoxes)
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
    private var boundingBoxes: List<Path> = emptyList()
    private var scaleX: Float = 1.0f
    private var scaleY: Float = 1.0f

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply {
            color =  argb(100, 0, 0, 0)
            style = Paint.Style.FILL
        }

        val rotationMatrix = Matrix()
        rotationMatrix.setScale(scaleX, scaleY)
        rotationMatrix.setRotate(90f, canvas.width / 2f, canvas.height / 2f)
        boundingBoxes.forEach { path ->
            val transformedPath = Path()
            path.transform(rotationMatrix, transformedPath)
            canvas.drawPath(transformedPath, paint)
        }
    }

    fun updateBoundingBoxes(boxes: List<Path>, arEnabled: Boolean) {
        if (arEnabled) {
            boundingBoxes = boxes

            val previewWidth = width.toFloat()
            val previewHeight = height.toFloat()

            val bounds = RectF()
            boxes.forEach { path ->
                path.computeBounds(bounds, true)
            }
            val originalWidth = bounds.width()
            val originalHeight = bounds.height()

            scaleX = previewWidth / originalWidth
            scaleY = previewHeight / originalHeight
        }
        else {
            boundingBoxes = emptyList()
        }
        invalidate() // Redraw the view with updated bounding boxes
    }
}

@Composable
fun CameraPreview(
    cameraProvider: LifecycleCameraController,
    onTextRecognition: (String, List<Path>) -> Unit,
    arEnabled: Boolean
) {
    val lifecycleOwner = LocalLifecycleOwner.current
    val context = LocalContext.current
    val boundingBoxOverlay = remember { BoundingBoxOverlay(context) }

    AndroidView(
        factory = { context ->
            val overlayContainer = LinearLayout(context).apply {
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                orientation = LinearLayout.HORIZONTAL

                val previewView = PreviewView(context).apply {
                    setBackgroundColor(rgb(234, 224, 231))
                    layoutParams = LinearLayout.LayoutParams(0, MATCH_PARENT).apply {
                        weight = 1f // Take remaining vertical space
                    }
                    scaleType = PreviewView.ScaleType.FILL_START
                    implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                    controller = bindPreview(
                        { text, boxes ->
                            boundingBoxOverlay.updateBoundingBoxes(boxes, arEnabled)
                            onTextRecognition(text, boxes)
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