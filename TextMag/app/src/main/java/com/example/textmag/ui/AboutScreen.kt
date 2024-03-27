package com.example.textmag.ui

import com.example.textmag.R
import android.annotation.SuppressLint
import android.icu.text.CaseMap.Title
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.ArrowBack
import androidx.compose.material3.Button
import androidx.compose.material3.CenterAlignedTopAppBar
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.material3.Scaffold
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.aspectRatio
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed

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
                    text = "Four Students from the University of Michigan attempt to fight Presbyopia and Macular Degeneration with tools to give the visually impaired accessibility through a cross platform text recognition and magnification app.",
                    modifier = Modifier.padding(top = 80.dp, start = 16.dp, end = 16.dp),
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Center
                )
            }
            item {
                Text(
                    text = "iOS Development Team",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 35.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
            itemsIndexed(teamiOS) { index, team ->
                TeamRow(team = team)
            }
            item {
                Text(
                    text = "Android Development Team",
                    modifier = Modifier.fillMaxWidth(),
                    fontSize = 35.sp,
                    color = MaterialTheme.colorScheme.primary,
                    textAlign = TextAlign.Center
                )
            }
            itemsIndexed(teamAndroid) { index, team ->
                TeamRow(team = team)
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
            itemsIndexed(coordinator) { index, team ->
                CoordinatorRow(team = team)
            }
        }
    }
}

@Composable
fun TeamRow(team: Team) {
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = team.imageId),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(top = 16.dp)
                .size(200.dp)
                .aspectRatio(1f)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            Box(
                modifier = Modifier
                    .background(Color.LightGray)
                    .border(3.dp, Color.Black, RoundedCornerShape(4.dp))
                    .padding(5.dp)
            ){
                Text(
                    text = "${team.name}\n${team.info}",
                    fontSize = 20.sp,
                    color = Color.Black,
                    textAlign = TextAlign.Start
                )
            }
            Text(
                text = "${team.description}",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Box(modifier = Modifier.size(16.dp))
            Box(
                modifier = Modifier
                    .background(MaterialTheme.colorScheme.primary)
                    .border(3.dp, MaterialTheme.colorScheme.primary, RoundedCornerShape(10.dp))
                    .padding(5.dp)
                    .clip(RoundedCornerShape(10.dp))
                    .width(150.dp)
                    .height(50.dp)
                    .align(Alignment.CenterHorizontally)
            ){
                Text(
                    text = team.name,
                    fontSize = 20.sp,
                    color = MaterialTheme.colorScheme.onPrimary,
                    textAlign = TextAlign.Center,
                    modifier = Modifier.align(Alignment.Center)
                )
            }
        }
    }
}

@Composable
fun CoordinatorRow(team : Team){
    Row(
        modifier = Modifier.fillMaxWidth()
    ) {
        Image(
            painter = painterResource(id = team.imageId),
            contentDescription = null,
            modifier = Modifier
                .clip(RoundedCornerShape(8.dp))
                .padding(top = 16.dp)
                .size(200.dp)
                .aspectRatio(1f)
        )
        Column(
            modifier = Modifier.weight(1f),
            verticalArrangement = Arrangement.Top
        ) {
            Text(
                text = "${team.name}\n${team.info}",
                fontSize = 20.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
            Text(
                text = "${team.description}",
                fontSize = 18.sp,
                color = Color.Black,
                textAlign = TextAlign.Start
            )
        }
    }
}

data class Team(
    val name: String,
    val info: String,
    val description: String,
    val imageId: Int
)

val teamiOS = listOf(
    Team(
        name = "Iskandar, Daniel",
        info = "University of Michigan CS-LSA '25",
        description = "Research/project management. Camera integration, image retrieval, and front end design in SwiftUI.",
        imageId = R.drawable.daniel
    ),
    Team(
        name = "Lu, Justin",
        info = "University of Michigan CS-CoE '24",
        description = "Research/project management. Apple Vision frameworks, language detection and translation, back end design in Swift UI.",
        imageId = R.drawable.justin
    )
)

val teamAndroid = listOf(
    Team(
        name = "Dong, Antony",
        info = "University of Michigan CSE-CoE '24",
        description = "CameraX Jetpack Compose implementation, front end design in Kotlin.",
        imageId = R.drawable.antony
    ),
    Team(
        name = "Parmar, Siddharth",
        info = "University of Michigan CS-CoE '24",
        description = "Android planning, research, and project management, camera integration, text translation, magnification, and app design in Kotlin",
        imageId = R.drawable.sid
    )
)

val coordinator = listOf(
    Team(
        name = "Dr. David R Chesney",
        info = "Teaching Professor, Electrical Engineering and Computer Science",
        description = "Coordinator and program director. Lead the team as an instructor in EECS 495: Software For Accessibility.",
        imageId = R.drawable.david
    )
)


@SuppressLint("UnusedMaterial3ScaffoldPaddingParameter")
@Composable
fun AboutScreen(
    onBackButtonClick: () -> Unit
) {
    Scaffold(
        topBar = { AboutScreenBar(onClick = onBackButtonClick) }
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
        navigationIcon = { IconButton(onClick = onClick) {
            Icon(
                Icons.Filled.ArrowBack,
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