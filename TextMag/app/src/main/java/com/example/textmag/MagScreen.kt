package com.example.textmag

import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.example.textmag.ui.MagViewModel
import com.example.textmag.ui.MainScreen
import com.example.textmag.ui.SettingsScreen
import com.example.textmag.ui.theme.TextMagTheme

enum class TextMagScreen() {
    Main,
    Settings
}

@Composable
fun TextMagApp(
    cameraProvider: LifecycleCameraController,
    viewModel: MagViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()
    val currentTheme = uiState.theme
    var darkTheme: Boolean

    when (currentTheme) {
        "System" -> darkTheme = isSystemInDarkTheme()
        "Dark" -> darkTheme = true
        else -> darkTheme = false
    }

    val typography = viewModel.getTypography()

    TextMagTheme(
        darkTheme = darkTheme,
        dynamicColor = uiState.dynamicThemeEnabled,
        typography = typography
    ) {
        NavHost(
            navController = navController,
            startDestination =  TextMagScreen.Main.name
        ) {
            composable(route = TextMagScreen.Main.name) {
                MainScreen(
                    cameraProvider = cameraProvider,
                    onSettingsButtonClick = { navController.navigate(TextMagScreen.Settings.name) },
                    onTextRecognition = { result, _, angles -> viewModel.updateRecognizedText(result) },
                    onFreezeButtonClick = { viewModel.toggleFreezeState() },
                    isTextFrozen = uiState.isTextFrozen,
                    recognizedText = uiState.recognizedText,
                    fontSize = uiState.fontSize.toString().slice(0..1)
                )
            }

            composable(route = TextMagScreen.Settings.name) {
                SettingsScreen(
                    onBackButtonClick = { navController.navigate(TextMagScreen.Main.name) },
                    curFont = uiState.font,
                    fontOptions = uiState.fontOptions,
                    onFontDropdownSelection = { newFont -> viewModel.setFont(newFont) },
                    curFontSize = uiState.fontSize.toString().slice(0..1),
                    fontSizeOptions = uiState.fontSizeOptions,
                    onFontSizeDropdownSelection = { newFontSize -> viewModel.setFontSize(newFontSize)},
                    curTheme = uiState.theme,
                    themeOptions = uiState.themeOptions,
                    onThemeDropdownSelection = { newTheme -> viewModel.updateTheme(newTheme) },
                    arEnabled = uiState.arEnabled,
                    onArToggle = { status -> viewModel.updateArEnabled(status) },
                    dynamicThemeEnabled = uiState.dynamicThemeEnabled,
                    onDynamicThemeToggle = { status -> viewModel.updateDynamicThemeEnabled(status)}
                )
            }
        }
    }
}