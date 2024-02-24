package com.example.textmag.ui

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
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
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.example.textmag.ui.theme.TestLayoutTheme

@Composable
fun MainScreen(modifier: Modifier = Modifier) {
    Scaffold(
        topBar = { MainScreenAppBar() },
        modifier = Modifier.fillMaxSize()
    ) {innerPadding ->
        MainScreenBody(modifier = Modifier.padding(innerPadding))
    }
}

@Composable
fun MainScreenBody(modifier: Modifier = Modifier) {
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
            Text(
                text = "Camera goes here",
                modifier = Modifier.padding(16.dp)
            )

        }

        Spacer(modifier = Modifier.padding(16.dp))

        Button(
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(121, 79, 130),
                contentColor = Color.White
            ),
            onClick = { /*TODO*/ }
        ) {
            Text(text = "Freeze Text")
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
                modifier = Modifier.padding(16.dp)
            )

        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun MainScreenAppBar() {
    // TODO: Add navigation to the next screen in the IconButton onClick
    // TODO: Remove hardcoding of colors to accommodate themes
    CenterAlignedTopAppBar(
        title = {
            Text(text = "Text Magnifier")
        },
        actions = { IconButton(onClick = { /*TODO*/ }) {
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

@Preview(showBackground = true)
@Composable
fun MainScreenPreview() {
    TestLayoutTheme {
        MainScreen()
    }
}
