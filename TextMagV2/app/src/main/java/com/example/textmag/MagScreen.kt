package com.example.textmag

import androidx.camera.lifecycle.ProcessCameraProvider
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
import com.google.common.util.concurrent.ListenableFuture

enum class TextMagScreen() {
    Main,
    Settings
}

@Composable
fun TextMagApp(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    viewModel: MagViewModel = viewModel(),
    navController: NavHostController = rememberNavController()
) {
    val uiState by viewModel.uiState.collectAsState()

    NavHost(
        navController = navController,
        startDestination =  TextMagScreen.Main.name
    ) {
        composable(route = TextMagScreen.Main.name) {
            MainScreen(
                cameraProviderFuture = cameraProviderFuture,
                onSettingsButtonClick = { navController.navigate(TextMagScreen.Settings.name) },
                onTextRecognition = { result -> viewModel.updateRecognizedText(result) },
                onFreezeButtonClick = { viewModel.toggleFreezeState() },
                isTextFrozen = uiState.isTextFrozen,
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
                onArToggle = { status -> viewModel.updateArEnabled(status) }
            )
        }
    }
}