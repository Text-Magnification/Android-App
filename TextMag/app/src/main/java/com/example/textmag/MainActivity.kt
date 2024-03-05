package com.example.textmag

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import com.example.textmag.ui.SettingsScreen
import com.example.textmag.ui.theme.TextMagTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            TextMagTheme {
                TextMagApp()
            }
        }
    }
}
