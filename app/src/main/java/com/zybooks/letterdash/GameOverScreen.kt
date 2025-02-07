package com.zybooks.letterdash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.letterdash.ui.components.HomeButton
import com.zybooks.letterdash.ui.components.PlayAgainButton
import com.zybooks.letterdash.ui.components.SettingsButton
import com.zybooks.letterdash.ui.components.TitleLogo

@Composable
fun GameOverScreen(modifier: Modifier = Modifier,
                   onPlayAgainClick: () -> Unit = {},
                   onHomeClick: () -> Unit = {},
                   onSettingsClick: () -> Unit = {},
                   gameViewModel: GameViewModel) {
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
            TitleLogo()
            Box(
                modifier = Modifier.padding(bottom = 16.dp),
                contentAlignment = Alignment.Center
            ) {
                Text(
                    text = "SCORE: ${gameViewModel.getScore()}",
                    color = Color.Black,
                    fontSize = 50.sp
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                PlayAgainButton(onClick = onPlayAgainClick)
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .padding(top = 6.dp)
                )                {
                    SettingsButton(onClick = onSettingsClick)
                    Spacer(modifier = Modifier.width(30.dp))
                    HomeButton(onClick = onHomeClick)
                }
            }
        }
    }
}

@Preview
@Composable
fun GameOverPreview() {
    GameOverScreen(gameViewModel = GameViewModel())
}