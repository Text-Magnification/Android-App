package com.example.textmag.ui

import android.annotation.SuppressLint
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Close
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.example.textmag.R

@Composable
fun AboutScreenBody() {
    Surface(
        color = MaterialTheme.colorScheme.surface
    ) {
        LazyColumn(
            modifier = Modifier.fillMaxSize()
        ) {
            item {
                Text(
                    text = "Four students from the University of Michigan attempt to fight presbyopia and myopia through a cross platform text recognition and magnification app to give the visually impaired accessibility.",
                    modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp),
                    fontSize = 20.sp,
                    textAlign = TextAlign.Center
                )
            }
            item {
                Spacer(modifier = Modifier.padding(16.dp))
            }
            item {
                Text(
                    text = "Android Team",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }

            items(teamAndroid) { team ->
                TeamRow(team = team)
            }

            item {
                Spacer(modifier = Modifier.padding(16.dp))
            }

            item {
                Text(
                    text = "iOS Team",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 36.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }

            items(teamiOS) { team ->
                TeamRow(team = team)
            }

            item {
                Spacer(modifier = Modifier.padding(16.dp))
            }

            item {
                Text(
                    text = "Coordinator",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 35.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
            item {
                TeamRow(coordinator)
                Spacer(modifier = Modifier.padding(16.dp))
            }
        }
    }
}

@Composable
fun TeamRow(team: Team) {
    ElevatedCard (
        modifier = Modifier
            .fillMaxWidth()
            .padding(8.dp)
    ) {
        Row(
            modifier = Modifier.fillMaxWidth()
        ) {
            Image(
                painter = painterResource(id = team.imageId),
                contentDescription = null,
                modifier = Modifier
                    .padding(8.dp)
                    .size(140.dp)
                    .aspectRatio(1f)
            )
            Column(
                horizontalAlignment = Alignment.CenterHorizontally,
                verticalArrangement = Arrangement.Top,
                modifier = Modifier
                    .weight(1f)
                    .padding(4.dp)
            ) {
                Button(
                    colors = ButtonDefaults.buttonColors(
                        containerColor = MaterialTheme.colorScheme.surface,
                        contentColor = MaterialTheme.colorScheme.onSurface,
                    ),
                    shape = RoundedCornerShape(16.dp),
                    onClick = { /*TODO*/ }
                ) {
                    Text(
                        text = "${team.name}",
                        fontSize = 20.sp,
                        fontWeight = FontWeight.Bold,
                        textAlign = TextAlign.Start,
                    )
                }
                Text(
                    text = "${team.description}",
                    fontSize = 14.sp,
                    textAlign = TextAlign.Center
                )
            }
        }
    }
}

data class Team(
    val name: String,
    val description: String,
    val imageId: Int
)

data class Prof(
    val name: String,
    val info: String,
    val description: String,
    val imageId: Int
)

val teamiOS = listOf(
    Team(
        name = "Daniel Iskandar",
        description = "Camera integration, image retrieval, and front end design",
        imageId = R.drawable.daniel
    ),
    Team(
        name = "Justin Lu",
        description = "Text recognition, language detection and translation, and back end design",
        imageId = R.drawable.justin
    )
)

val teamAndroid = listOf(
    Team(
        name = "Antony Dong",
        description = "Camera, AR integration, and front end design",
        imageId = R.drawable.antony
    ),
    Team(
        name = "Siddharth Parmar",
        description = "Camera integration, text translation, magnification, and full-stack app design",
        imageId = R.drawable.sid
    )
)

val coordinator = Team(
    name = "Dr. David R Chesney",
    description = "Coordinator and program director. Led the team as an instructor in EECS 495: Software For Accessibility.",
    imageId = R.drawable.david
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutScreen(
    onCloseButtonClick: () -> Unit
) {
    Scaffold(
        topBar = { AboutScreenBar(onClick = onCloseButtonClick) }
    ) {
        AboutScreenBody()
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun AboutScreenBar(
    onClick: () -> Unit
) {
    CenterAlignedTopAppBar(
        title = {
            Text(text = "About Us")
        },
        actions = { IconButton(onClick = onClick) {
            Icon(
                Icons.Filled.Close,
                contentDescription = "Close Button",
                tint = MaterialTheme.colorScheme.onPrimary
            )
        }},
        colors = TopAppBarDefaults.topAppBarColors(
            containerColor = MaterialTheme.colorScheme.primary,
            titleContentColor = MaterialTheme.colorScheme.onPrimary
        )
    )
}