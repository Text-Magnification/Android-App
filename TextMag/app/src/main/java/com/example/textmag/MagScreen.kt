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
import com.example.textmag.ui.AboutScreen
import com.example.textmag.ui.MagViewModel
import com.example.textmag.ui.MainScreen
import com.example.textmag.ui.SettingsScreen
import com.example.textmag.ui.theme.TextMagTheme

enum class TextMagScreen() {
    Main,
    Settings,
    AboutUs
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
                    onTextRecognition = { result, _ -> viewModel.updateRecognizedText(result) },
                    arEnabled = uiState.arEnabled,
                    onFreezeButtonClick = { viewModel.toggleFreezeState() },
                    isTextFrozen = uiState.isTextFrozen,
                    stabilizationTarget = uiState.stabilizationTarget,
                    recognizedText = uiState.recognizedText,
                    script = uiState.script,
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
                    onFontSizeDropdownSelection = { newFontSize -> viewModel.setFontSize(newFontSize) },
                    curScript = uiState.script,
                    scriptOptions = uiState.scriptOptions,
                    onScriptSelection = { newScript -> viewModel.updateScript(newScript) },
                    curTheme = uiState.theme,
                    themeOptions = uiState.themeOptions,
                    onThemeDropdownSelection = { newTheme -> viewModel.updateTheme(newTheme) },
                    arEnabled = uiState.arEnabled,
                    onArToggle = { status -> viewModel.updateArEnabled(status) },
                    dynamicThemeEnabled = uiState.dynamicThemeEnabled,
                    target = uiState.stabilizationTarget.toFloat(),
                    onTargetChange = { target -> viewModel.updateStabilizationTarget(target) },
                    onAboutUsClick = { navController.navigate(TextMagScreen.AboutUs.name) },
                    onDynamicThemeToggle = { status -> viewModel.updateDynamicThemeEnabled(status)}
                )
            }

            composable(route = TextMagScreen.AboutUs.name) {
                AboutScreen(
                    onCloseButtonClick = { navController.navigate(TextMagScreen.Settings.name) }
                )
            }
        }
    }
}