package com.example.textmag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.camera.view.LifecycleCameraController

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val cameraProvider = LifecycleCameraController(baseContext)

        setContent {
            TextMagApp(cameraProvider)
        }
    }
}
