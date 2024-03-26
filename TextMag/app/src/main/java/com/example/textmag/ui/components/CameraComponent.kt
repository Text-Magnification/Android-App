package com.example.textmag.ui.components

import android.content.Context
import android.graphics.Canvas
import android.graphics.Color.argb
import android.graphics.Color.rgb
import android.graphics.Paint
import android.graphics.Path
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


fun bindPreview(
    onTextRecognition: (String, List<Path>) -> Unit,
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
            val boundingBoxes = mutableListOf<Path>()

            blocks.forEach { block ->
                val path = Path()
                path.moveTo((block.cornerPoints?.get(0)?.x ?: 0).toFloat(), (block.cornerPoints?.get(0)?.y ?: 0).toFloat())
                for (i in 1..3) {
                    path.lineTo((block.cornerPoints?.get(i)?.x ?: 0).toFloat(), (block.cornerPoints?.get(i)?.y ?: 0).toFloat())
                }
                boundingBoxes.add(path)
            }

            onTextRecognition(result?.getValue(textRecognizer)?.text ?: "", boundingBoxes)
            return@MlKitAnalyzer
        }
    )
    cameraProvider.bindToLifecycle(lifecycleOwner)
    return cameraProvider
}

class BoundingBoxOverlay(context : Context) : View(context) {
    private var boundingBoxes: List<Path> = emptyList()

    override fun onDraw(canvas: Canvas) {
        super.onDraw(canvas)

        val paint = Paint().apply {
            color =  argb(0.4F, 0F, 0F, 0F)
            style = Paint.Style.FILL
        }

        boundingBoxes.forEach { path ->
            canvas.drawPath(path, paint)
        }
    }

    fun updateBoundingBoxes(boxes: List<Path>) {
        boundingBoxes = boxes
        invalidate() // Redraw the view with updated bounding boxes
    }
}

@Composable
fun CameraPreview(
    cameraProvider: LifecycleCameraController,
    onTextRecognition: (String, List<Path>) -> Unit
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
                        { text, boxes ->
                            boundingBoxOverlay.updateBoundingBoxes(boxes)
                            onTextRecognition(text, boxes)
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