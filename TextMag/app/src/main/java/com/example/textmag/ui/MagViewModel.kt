package com.example.textmag.ui

import androidx.compose.ui.unit.dp
import androidx.lifecycle.ViewModel
import com.example.textmag.data.MagUiState
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

    // TODO: Add a reset to default options in settings
}