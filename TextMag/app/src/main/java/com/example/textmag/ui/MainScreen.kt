package com.example.textmag.ui

import android.graphics.Path
import androidx.camera.view.LifecycleCameraController
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
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
import androidx.compose.material3.MaterialTheme
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

@Composable
fun MainScreen(
    cameraProvider: LifecycleCameraController,
    onSettingsButtonClick: () -> Unit,
    onTextRecognition: (String, List<Path>) -> Unit,
    arEnabled: Boolean,
    onFreezeButtonClick: () -> Unit,
    isTextFrozen: Boolean,
    fontSize: String = "14",
    recognizedText: String = "",
    modifier: Modifier = Modifier
) {
    Scaffold(
        topBar = { MainScreenAppBar(onSettingsButtonClick) },
        modifier = modifier
    ) {innerPadding ->
        MainScreenBody(
            cameraProvider = cameraProvider,
            onTextRecognition = onTextRecognition,
            arEnabled = arEnabled,
            onFreezeButtonClick = onFreezeButtonClick,
            isTextFrozen = isTextFrozen,
            fontSize = fontSize,
            recognizedText = recognizedText,
            modifier = Modifier.padding(innerPadding)
        )
    }
}

@Composable
fun MainScreenBody(
    cameraProvider: LifecycleCameraController,
    onTextRecognition: (String, List<Path>) -> Unit,
    arEnabled: Boolean,
    onFreezeButtonClick: () -> Unit,
    isTextFrozen: Boolean,
    fontSize: String,
    recognizedText: String = "",
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
            CameraPreview(
                cameraProvider = cameraProvider,
                onTextRecognition = onTextRecognition,
                arEnabled = arEnabled
            )
        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = MaterialTheme.colorScheme.primary,
                contentColor = MaterialTheme.colorScheme.onPrimary
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
                containerColor = MaterialTheme.colorScheme.background
            ),
            modifier = Modifier
                .size(width = 300.dp, height = 300.dp)
        ) {
            LazyColumn {
                item {
                    Text(
                        text = recognizedText,
                        fontSize = fontSize.toInt().sp,
                        modifier = Modifier.padding(16.dp),
                        color = MaterialTheme.colorScheme.onBackground,
                        lineHeight = (fontSize.toInt() * 1.15).sp
                    )
                }
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenAppBar(onClick: () -> Unit) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = stringResource(R.string.app_name))
        },
        actions = { IconButton(onClick = onClick) {
            Icon(
                Icons.Filled.Settings,
                contentDescription = "Settings",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}
