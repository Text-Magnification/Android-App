package com.example.textmag.data

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class MagUiState(
    val font: String = "Roboto",
    val fontOptions: List<String> = listOf("Roboto", "Open Dyslexic"),
    val fontSize: Dp = 14.dp,
    val fontSizeOptions: List<String> = listOf("14", "16", "18", "20", "24", "28", "32"),
    val theme: String = "System",
    val dynamicThemeEnabled: Boolean = false,
    val themeOptions: List<String> = listOf("System", "Light", "Dark"),
    val arEnabled: Boolean = false,
    val recognizedText: String = "",
    val stabilizationTarget: Int = 50,
    val script: String = "Latin",
    val scriptOptions: List<String> = listOf("Latin", "Devanagari", "Korean", "Chinese", "Japanese"),
    val isTextFrozen: Boolean = false
)