package com.zybooks.letterdash

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Text
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.letterdash.ui.theme.LetterDashTheme
import com.zybooks.letterdash.ui.components.SettingsButton
import com.zybooks.letterdash.ui.components.TitleLogo
import com.zybooks.letterdash.ui.components.HowToPlayButton
import com.zybooks.letterdash.ui.components.PlayButton


@Composable
fun HomeScreen(modifier: Modifier = Modifier) {
    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White,Color(0xff8ecdff)),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            ),
        contentAlignment = Alignment.Center
    ) {
        Column(
            verticalArrangement = Arrangement.Center,
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            var titleLogo = TitleLogo()
            titleLogo.Content()
            Box(
                modifier = Modifier.padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "HIGH SCORE: ",
                    color = Color.Black,
                    fontSize = 50.sp
                )
            }
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                var playButton = PlayButton()
                playButton.Content()
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .padding(top = 6.dp)
                ) {
                    var settingsButton = SettingsButton()
                    settingsButton.Content()

                    Spacer(modifier = Modifier.width(30.dp))

                    var howToPlayButton = HowToPlayButton()
                    howToPlayButton.Content()
                }
            }
        }
    }
}


@Preview
@Composable
fun HomePreview() {
    LetterDashTheme {
        HomeScreen()
    }
}