import android.content.Context
import android.graphics.*
import android.media.Image
import android.view.ViewGroup.LayoutParams.MATCH_PARENT
import android.widget.LinearLayout
import androidx.annotation.OptIn
import androidx.camera.core.ExperimentalGetImage
import androidx.camera.core.ImageAnalysis
import androidx.camera.core.ImageProxy
import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.camera.view.LifecycleCameraController
import androidx.camera.view.PreviewView
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.compose.ui.viewinterop.AndroidView
import androidx.core.content.ContextCompat
import androidx.lifecycle.LifecycleOwner
import com.google.mlkit.vision.common.InputImage
import com.google.mlkit.vision.text.TextRecognition
import com.google.mlkit.vision.text.TextRecognizer
import com.google.mlkit.vision.text.latin.TextRecognizerOptions
import java.nio.ByteBuffer
import java.util.concurrent.ExecutorService
import java.util.concurrent.Executors

@OptIn(ExperimentalGetImage::class)
fun bindPreview(
    cameraProvider: LifecycleCameraController,
    lifecycleOwner: LifecycleOwner,
    context: Context,
    executor: ExecutorService,
    onTextRecognition: (String) -> Unit 
): LifecycleCameraController {
    val textRecognizer: TextRecognizer = TextRecognition.getClient(TextRecognizerOptions.DEFAULT_OPTIONS)

    cameraProvider.setImageAnalysisAnalyzer(
        executor,
        ImageAnalysis.Analyzer { imageProxy ->
            val rotationDegrees = imageProxy.imageInfo.rotationDegrees
            val image = imageProxy.image
            if (image != null) {
                val inputImage = InputImage.fromMediaImage(image, rotationDegrees)

                textRecognizer.process(inputImage)
                    .addOnSuccessListener { text ->
                        // Handle text recognition success
                        onTextRecognition(text.text) // Invoke the onTextRecognition callback
                        drawTextOverPreview(imageProxy, text)
                    }
                    .addOnFailureListener { e ->
                        // Handle text recognition failure
                        e.printStackTrace()
                    }
                    .addOnCompleteListener {
                        // Close the image proxy
                        imageProxy.close()
                    }
            }
        }
    )
    cameraProvider.bindToLifecycle(lifecycleOwner)
    return cameraProvider
}

private fun drawTextOverPreview(imageProxy: ImageProxy, text: com.google.mlkit.vision.text.Text) {
    val bitmap = imageProxy.toBitmap()
    val canvas = Canvas(bitmap)
    val paint = Paint()
    paint.color = Color.RED
    paint.style = Paint.Style.STROKE
    paint.strokeWidth = 4.0f

    val textPaint = Paint()
    textPaint.color = Color.RED
    textPaint.textSize = 48f

    text.textBlocks.forEach { block ->
        val boundingBox = block.boundingBox
        if (boundingBox != null) {
            canvas.drawRect(boundingBox, paint)
        }
        if (boundingBox != null) {
            canvas.drawText(block.text, boundingBox.left.toFloat(), boundingBox.top.toFloat(), textPaint)
        }
    }

    // Update the imageProxy with the drawn bitmap
    updateImageProxy(imageProxy, bitmap)
}

fun ImageProxy.toBitmap(): Bitmap {
    val buffer = planes[0].buffer
    val bytes = ByteArray(buffer.remaining())
    buffer.get(bytes)
    return BitmapFactory.decodeByteArray(bytes, 0, bytes.size, null)
}

fun updateImageProxy(imageProxy: ImageProxy, bitmap: Bitmap) {
    val buffer = imageProxy.planes[0].buffer
    buffer.rewind()
    bitmap.copyPixelsToBuffer(buffer)
    imageProxy.close()
}

@Composable
fun CameraPreview(
    cameraProvider: LifecycleCameraController,
    onTextRecognition: (String) -> Unit
) {
    val context = LocalContext.current
    val lifecycleOwner = LocalLifecycleOwner.current

    val executor = Executors.newSingleThreadExecutor()

    AndroidView(
        factory = { ctx ->
            PreviewView(ctx).apply {
                setBackgroundColor(Color.BLACK)
                layoutParams = LinearLayout.LayoutParams(MATCH_PARENT, MATCH_PARENT)
                scaleType = PreviewView.ScaleType.FILL_START
                implementationMode = PreviewView.ImplementationMode.COMPATIBLE
                controller = bindPreview(cameraProvider, lifecycleOwner, context, executor, onTextRecognition)
            }
        }
    )
}
