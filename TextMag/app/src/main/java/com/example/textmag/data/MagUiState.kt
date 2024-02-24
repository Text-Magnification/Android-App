package com.example.textmag.data

import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class MagUiState(
    val font: String = "Roboto",
    val fontSize: Dp = 16.dp,
    val theme: String = "System",
    val arEnabled: Boolean = false,
)