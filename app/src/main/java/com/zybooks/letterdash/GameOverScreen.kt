package com.zybooks.letterdash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.letterdash.ui.components.DifficultyButton
import com.zybooks.letterdash.ui.components.HomeButton
import com.zybooks.letterdash.ui.components.PlayAgainButton
import com.zybooks.letterdash.ui.components.SettingsButton
import com.zybooks.letterdash.ui.components.TitleLogo

@Composable
fun GameOverScreen(modifier: Modifier = Modifier,
                   onPlayAgainClick: () -> Unit = {},
                   onHomeClick: () -> Unit = {},
                   gameViewModel: GameViewModel) {

    var showSettings by remember { mutableStateOf(false) }
    gameViewModel.soundEnabled.observeAsState("").value
    gameViewModel.difficulty.observeAsState("").value

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
                    fontSize = 46.sp
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                PlayAgainButton(onClick = onPlayAgainClick)
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .padding(top = 6.dp)
                )                {
                    SettingsButton(onClick = {showSettings = true})
                    Spacer(modifier = Modifier.width(30.dp))
                    HomeButton(onClick = onHomeClick)
                }
            }
        }
    }
    if (showSettings) {
        AlertDialog(
            title = {
                Column(horizontalAlignment = Alignment.CenterHorizontally) {
                    Row(verticalAlignment = Alignment.CenterVertically) {
                        Icon(
                            imageVector = if (gameViewModel.isSoundEnabled()) Icons.AutoMirrored.Filled.VolumeUp else
                                Icons.AutoMirrored.Filled.VolumeOff,
                            contentDescription = if (gameViewModel.isSoundEnabled()) "Sound Enabled" else
                                "Sound Disabled",
                            modifier = Modifier
                                .size(40.dp)
                                .clickable {
                                    gameViewModel.setSoundEnabled(!gameViewModel.isSoundEnabled())
                                }
                        )
                        Text(text = "Sound: ${if (gameViewModel.isSoundEnabled()) "On" else "Off"}")
                    }

                    Spacer(modifier = Modifier.height(16.dp))

                    Row(verticalAlignment = Alignment.CenterVertically,
                        horizontalArrangement = Arrangement.SpaceBetween ) {
                        DifficultyButton(color = Color(0xFF009539), text = "Easy", onClick = {
                            gameViewModel.setDifficulty(Difficulty.EASY)
                        },
                            modifier = modifier.alpha(when(gameViewModel.getDifficulty()){
                                Difficulty.EASY -> 1F
                                else -> 0.5F
                            })
                                .border(when(gameViewModel.getDifficulty()){
                                    Difficulty.EASY -> 2.dp
                                    else -> 0.dp
                                }, Color.Black,
                                    shape = RoundedCornerShape(40)
                                )
                                .weight(1F)
                                .padding(0.dp)
                        )
                        DifficultyButton(color = Color(0xffddda16), text = "Normal", onClick = {
                            gameViewModel.setDifficulty(Difficulty.NORMAL)
                        },
                            modifier =modifier.alpha(when(gameViewModel.getDifficulty()){
                                Difficulty.NORMAL -> 1F
                                else -> 0.5F
                            })
                                .border(when(gameViewModel.getDifficulty()){
                                    Difficulty.NORMAL -> 2.dp
                                    else -> 0.dp
                                }, Color.Black,
                                    shape = RoundedCornerShape(40)
                                )
                                .weight(1F)
                                .padding(0.dp)
                        )
                        DifficultyButton(color = Color.Red, text = "Hard", onClick = {
                            gameViewModel.setDifficulty(Difficulty.HARD)
                        },
                            modifier = modifier.alpha(when(gameViewModel.getDifficulty()){
                                Difficulty.HARD -> 1F
                                else -> 0.5F
                            })
                                .border(when(gameViewModel.getDifficulty()){
                                    Difficulty.HARD -> 2.dp
                                    else -> 0.dp
                                }, Color.Black,
                                    shape = RoundedCornerShape(40)
                                )
                                .weight(1F)
                                .padding(0.dp)
                        )
                    }
                }
            },
            onDismissRequest = { showSettings = false },
            confirmButton = {
                Column(
                    modifier = Modifier.fillMaxWidth(),
                    horizontalAlignment = Alignment.CenterHorizontally
                ) {
                    Button(onClick = { showSettings = false }) {
                        Text(text = "OK")
                    }
                }
            }
        )
    }
}

@Preview
@Composable
fun GameOverPreview() {
    GameOverScreen(gameViewModel = GameViewModel())
}