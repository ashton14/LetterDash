package com.zybooks.letterdash

import androidx.compose.foundation.layout.Column
import androidx.compose.runtime.Composable
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.automirrored.filled.VolumeOff
import androidx.compose.material.icons.automirrored.filled.VolumeUp
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.Text
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.zybooks.letterdash.ui.components.DifficultyButton
import com.zybooks.letterdash.ui.theme.LetterDashTheme
import com.zybooks.letterdash.ui.components.SettingsButton
import com.zybooks.letterdash.ui.components.TitleLogo
import com.zybooks.letterdash.ui.components.HowToPlayButton
import com.zybooks.letterdash.ui.components.PlayButton
import kotlinx.coroutines.launch


@Composable
fun HomeScreen(
    modifier: Modifier = Modifier,
    onPlayClick: () -> Unit = {},
    gameViewModel: GameViewModel
) {
    var showTutorial by remember { mutableStateOf(false) }
    var showSettings by remember { mutableStateOf(false) }
    gameViewModel.soundEnabled.observeAsState("").value
    gameViewModel.difficulty.observeAsState("").value

    //data store
    val store = AppStorage(LocalContext.current)
    val appPrefs = store.appPreferencesFlow.collectAsStateWithLifecycle(AppPreferences())
    val coroutineScope = rememberCoroutineScope()

    gameViewModel.setSoundEnabled(appPrefs.value.soundEnabled)
    gameViewModel.setDifficulty(appPrefs.value.difficulty)
    gameViewModel.setHighScore(appPrefs.value.highScore)


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
                                            coroutineScope.launch {
                                                store.updateSoundEnabled(gameViewModel.isSoundEnabled())
                                            }
                                        }
                                )
                                Text(text = "Sound: ${if (gameViewModel.isSoundEnabled()) "On" else "Off"}")
                            }

                            Spacer(modifier = Modifier.height(16.dp))

                            Row(verticalAlignment = Alignment.CenterVertically,
                                horizontalArrangement = Arrangement.SpaceBetween ) {
                                DifficultyButton(color = Color(0xFF009539), text = "Easy", onClick = {
                                    gameViewModel.setDifficulty(Difficulty.EASY)
                                    coroutineScope.launch {
                                        store.updateDifficulty(gameViewModel.getDifficulty())
                                    }
                                },
                                    modifier = modifier.alpha(when(gameViewModel.getDifficulty()){
                                        Difficulty.EASY -> 1F
                                        else -> 0.5F
                                    })
                                        .border(when(gameViewModel.getDifficulty()){
                                            Difficulty.EASY -> 2.dp
                                            else -> 0.dp
                                        }, Color.Black,
                                            shape = RoundedCornerShape(40))
                                        .weight(1F)
                                        .padding(0.dp)
                                    )
                                DifficultyButton(color = Color(0xffddda16), text = "Normal", onClick = {
                                    gameViewModel.setDifficulty(Difficulty.NORMAL)
                                    coroutineScope.launch {
                                        store.updateDifficulty(gameViewModel.getDifficulty())
                                    }
                                },
                                    modifier =modifier.alpha(when(gameViewModel.getDifficulty()){
                                        Difficulty.NORMAL -> 1F
                                        else -> 0.5F
                                    })
                                        .border(when(gameViewModel.getDifficulty()){
                                            Difficulty.NORMAL -> 2.dp
                                            else -> 0.dp
                                        }, Color.Black,
                                            shape = RoundedCornerShape(40))
                                        .weight(1F)
                                        .padding(0.dp)
                                )
                                DifficultyButton(color = Color.Red, text = "Hard", onClick = {
                                    gameViewModel.setDifficulty(Difficulty.HARD)
                                    coroutineScope.launch {
                                        store.updateDifficulty(gameViewModel.getDifficulty())
                                    }
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
                            Button(onClick = {
                                showSettings = false
                                coroutineScope.launch {
                                    store.updateDifficulty(gameViewModel.getDifficulty())
                                }
                            }) {
                                Text(text = "OK")
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