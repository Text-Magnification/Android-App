package com.example.textmag.ui

import androidx.camera.lifecycle.ProcessCameraProvider
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.textmag.R
import com.example.textmag.ui.components.CameraPreview
import com.google.common.util.concurrent.ListenableFuture

@Composable
fun MainScreen(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    onSettingsButtonClick: () -> Unit,
    onTextRecognition: (String) -> Unit,
    onFreezeButtonClick: () -> Unit,
    isTextFrozen: Boolean,
    fontSize: String = "14",
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { MainScreenAppBar(onSettingsButtonClick) },
        modifier = modifier
    ) {innerPadding ->
        MainScreenBody(
            cameraProviderFuture = cameraProviderFuture,
            onTextRecognition = onTextRecognition,
            onFreezeButtonClick = onFreezeButtonClick,
            isTextFrozen = isTextFrozen,
            fontSize = fontSize,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun MainScreenBody(
    cameraProviderFuture: ListenableFuture<ProcessCameraProvider>,
    onTextRecognition: (String) -> Unit,
    onFreezeButtonClick: () -> Unit,
    isTextFrozen: Boolean,
    fontSize: String,
    modifier: Modifier = Modifier
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier
            .padding(20.dp)
            .fillMaxWidth()
    ) {
        Card(
            colors = CardDefaults.cardColors(
                containerColor = Color(234, 224, 231)
            ),
            modifier = Modifier
                .size(width = 300.dp, height = 300.dp)
        ) {
//            Text(
//                text = "Camera goes here",
//                modifier = Modifier.padding(16.dp)
//            )
            CameraPreview(cameraProviderFuture = cameraProviderFuture)
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(121, 79, 130),
                contentColor = Color.White
            ),
            onClick = onFreezeButtonClick
        ) {
            Text(
                text = if (isTextFrozen) stringResource(R.string.unfreeze_button_label) else stringResource(R.string.freeze_button_label)
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        ElevatedCard(
            colors = CardDefaults.cardColors(
                containerColor = Color(234, 224, 231)
            ),
            modifier = Modifier
                .size(width = 300.dp, height = 300.dp)
        ) {
            Text(
                text = "Text goes here",
                fontSize = fontSize.toInt().sp,
                modifier = Modifier.padding(16.dp)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenAppBar(onClick: () -> Unit) {
    // TODO: Add navigation to the next screen in the IconButton onClick
    // TODO: Remove hardcoding of colors to accommodate themes
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },
        actions = { IconButton(onClick = onClick) {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Settings",
                tint = Color.White
            )
        }},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = Color(121, 79, 130),
            titleContentColor = Color.White
        )
    )
}

//@Preview(showBackground = true)
//@Composable
//fun MainScreenPreview() {
//    TextMagTheme {
//        MainScreen(
//            cameraProviderFuture = ListenableFuture<ProcessCameraProvider>(),
//            onSettingsButtonClick = {},
//            onTextRecognition = { res: String -> },
//            onFreezeButtonClick = {},
//            isTextFrozen = false,
//            modifier = Modifier.fillMaxSize()
//        )
//    }
//}
