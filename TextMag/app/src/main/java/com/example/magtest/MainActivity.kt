package com.example.magtest

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
import androidx.compose.material3.Button
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
import com.example.magtest.ui.theme.MagTestTheme

class MainActivity : ComponentActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            MagTestTheme {
                // A surface container using the 'background' color from the theme
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    TwoBoxesExample()
                }
            }
        }
    }
}

@Composable
fun TwoBoxesExample(){
    Box(
        modifier = Modifier.fillMaxSize()
    ) {
        Text(
            text = "Text Magnification",
            modifier = Modifier
                .padding(16.dp)
                .align(Alignment.TopCenter),
            fontSize = 22.sp,
            fontWeight = FontWeight.Bold
        )
        Box(
            modifier = Modifier
                .size(450.dp, 400.dp)
                .padding(16.dp)
                .padding(top = 50.dp)
                .background(Color.White) // Background color
                .border(
                    width = 10.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                ) // Border
                .align(Alignment.TopCenter)
        ) {
            Image(
                painter = painterResource(id = R.drawable.my_image), // Replace with your image resource
                contentDescription = null,
                modifier = Modifier
                    .fillMaxSize()
                    .clip(RoundedCornerShape(8.dp))
            )
        }
        Button(
            onClick = { /* Handle button click */ },
            modifier = Modifier
                .padding(8.dp)
                .align(Alignment.Center)
        ) {
            Text("FreezeText")
        }
        Box(
            modifier = Modifier
                .size(450.dp, 400.dp)
                .padding(16.dp)
                .background(Color.White) // Background color
                .border(
                    width = 10.dp,
                    color = Color.Black,
                    shape = RoundedCornerShape(8.dp)
                ) // Border
                .align(Alignment.BottomCenter)
        ) {
            Text(
                text = "Placeholder",
                modifier = Modifier
                    .padding(16.dp)
                    .align(Alignment.Center),
                fontSize = 22.sp,
                fontWeight = FontWeight.Bold
            )
        }
    }
}