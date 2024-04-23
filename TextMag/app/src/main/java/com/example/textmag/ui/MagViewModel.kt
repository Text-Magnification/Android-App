package com.example.textmag.ui

import androidx.compose.material3.Typography
import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.textmag.data.MagUiState
import com.example.textmag.ui.theme.dyslexicTypography
import com.example.textmag.ui.theme.robotoTypography
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.update

class MagViewModel: ViewModel() {
    private val _uiState = MutableStateFlow(MagUiState())
    val uiState: StateFlow<MagUiState> = _uiState.asStateFlow()

    fun setFont(font: String) {
        _uiState.update { currentState ->
            currentState.copy(font = font)
        }
    }

    fun getTypography() : Typography {
        when(uiState.value.font) {
            "Roboto" -> return robotoTypography
        }
        return dyslexicTypography
    }

    fun setFontSize(fontSize: String) {
        _uiState.update { currentState ->
            currentState.copy(fontSize = fontSize.toInt().dp)
        }
    }

    fun updateRecognizedText(text: String) {
        if (!uiState.value.isTextFrozen) {
            _uiState.update { currentState ->
                currentState.copy(recognizedText = text)
            }
        }
    }

    fun updateTheme(curTheme: String) {
        _uiState.update { currentState ->
            currentState.copy(theme = curTheme)
        }
    }

    fun toggleFreezeState() {
        _uiState.update { currentState ->
            currentState.copy(isTextFrozen = !uiState.value.isTextFrozen)
        }
    }

    fun updateArEnabled(status: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(arEnabled = status)
        }
    }

    fun updateDynamicThemeEnabled(status: Boolean) {
        _uiState.update { currentState ->
            currentState.copy(dynamicThemeEnabled = status)
        }
    }

    fun updateStabilizationTarget(target: Float) {
        _uiState.update { currentState ->
            currentState.copy(stabilizationTarget = target.toInt())
        }
    }

    fun updateScript(newScript: String) {
        _uiState.update { currentState ->
            currentState.copy(script = newScript)
        }
    }

    // TODO: Add a reset to default options in settings
}