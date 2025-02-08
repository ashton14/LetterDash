package com.zybooks.letterdash

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Checkbox
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import com.zybooks.letterdash.ui.theme.LetterDashTheme
import com.zybooks.letterdash.ui.components.SettingsButton
import com.zybooks.letterdash.ui.components.TitleLogo
import com.zybooks.letterdash.ui.components.HowToPlayButton
import com.zybooks.letterdash.ui.components.PlayButton


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit = {},
    gameViewModel: GameViewModel
) {
    var showTutorial by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    var soundEnabled by remember { mutableStateOf(true) }
    var vibrationEnabled by remember { mutableStateOf(true) }


    Box(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xff8ecdff)),
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
                    text = "HIGH SCORE: ${gameViewModel.getHighScore()}",
                    color = Color.Black,
                    fontSize = 46.sp
                )
            }
            Spacer(modifier = Modifier.height(30.dp))
            Column(horizontalAlignment = Alignment.CenterHorizontally) {
                PlayButton(onClick = onPlayClick)
                Row(horizontalArrangement = Arrangement.SpaceBetween,
                    modifier = modifier
                        .padding(top = 6.dp)
                ) {
                    SettingsButton(onClick = {
                        showSettings = true
                    })

                    Spacer(modifier = Modifier.width(30.dp))

                    HowToPlayButton(onClick = {
                        showTutorial = true
                    })

                }
            }
            if (showTutorial) {
                AlertDialog(
                    title = {
                        Column {
                            Text(text = "How To Play")
                            Text(text = stringResource(R.string.instructions), fontSize = 12.sp)
                            Text(text = "Scoring")
                            Text(text = stringResource(R.string.scoring), fontSize = 12.sp)
                        }
                    },
                    onDismissRequest = { showTutorial = false },
                    confirmButton = {
                        Column(
                            modifier = Modifier.fillMaxWidth(),
                            horizontalAlignment = Alignment.CenterHorizontally
                        ) {
                            Button(onClick = { showTutorial = false }) {
                                Text(text = "Close")
                            }
                        }
                    }
                )
            }

            if (showSettings) {
                AlertDialog(
                    title = {
                        Column {
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = soundEnabled, onCheckedChange = { soundEnabled = it })
                                Text("Sound")
                            }
                            Row(verticalAlignment = Alignment.CenterVertically) {
                                Checkbox(checked = vibrationEnabled, onCheckedChange = { vibrationEnabled = it })
                                Text("Vibration")
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
                                Text(text = "Close")
                            }
                        }
                    }
                )
            }
        }
    }
}


@Preview
@Composable
fun HomePreview() {
    LetterDashTheme {
        HomeScreen(modifier = Modifier,
            gameViewModel = GameViewModel(), onPlayClick = {})
    }
}