package com.zybooks.letterdash

import android.annotation.SuppressLint
import android.content.Context
import android.media.MediaPlayer
import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.zybooks.letterdash.ui.components.Keyboard
import com.zybooks.letterdash.ui.components.LetterTile
import com.zybooks.letterdash.ui.components.SkipButton
import com.zybooks.letterdash.ui.components.Timer
import kotlinx.coroutines.launch


@SuppressLint("RememberReturnType")
@OptIn(UnstableApi::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    onTimerEnd: () -> Unit = {},
    gameViewModel: GameViewModel
) {


    gameViewModel.currentWord.observeAsState("").value

    val store = AppStorage(LocalContext.current)
    val coroutineScope = rememberCoroutineScope()
    val context = LocalContext.current

    val keyType = remember { MediaPlayer.create(context, R.raw.mixkit_typewriter_soft_click_1125) }
    val valid = remember { MediaPlayer.create(context, R.raw.mixkit_game_click_1114) }
    val invalid = remember { MediaPlayer.create(context, R.raw.mixkit_negative_tone_interface_tap_2569) }
    val skip = remember { MediaPlayer.create(context, R.raw.wrong_answer_126515) }


    DisposableEffect(Unit) {
        onDispose {
            keyType?.release()
            valid?.release()
            invalid?.release()
        }
    }

    Column(
        modifier = modifier
            .fillMaxSize()
            .background(
                Brush.verticalGradient(
                    colors = listOf(Color.White, Color(0xff8ecdff)),
                    startY = 0f,
                    endY = Float.POSITIVE_INFINITY
                )
            ),
        horizontalAlignment = Alignment.CenterHorizontally,
        verticalArrangement = Arrangement.Center
    ) {
        // Timer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center

        ) {
            Timer(onTimerEnd = {
                onTimerEnd()
                coroutineScope.launch {
                    store.updateHighScore(gameViewModel.getHighScore())
                }})
        }

        Text(
            modifier = modifier.padding(16.dp),
            text = "SCORE: ${gameViewModel.getScore()}",
            fontSize = 30.sp,

        )

        // Text Field
        OutlinedTextField(
            value = gameViewModel.getCurrentWord(),
            onValueChange = { gameViewModel.setCurrentWord(it)},
            readOnly = true,
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .border(3.dp, Color.Black),
            textStyle = TextStyle(fontSize = 30.sp)

        )

        // Letters
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp),
            horizontalArrangement = Arrangement.Center
        ) {
            val letters = gameViewModel.getCurrentLetters()
            for (i in letters.indices) {
                LetterTile(letter = letters[i])
                if (i < letters.size - 1) {
                    Spacer(modifier = Modifier.width(16.dp))
                }
            }

            }

        Spacer(modifier = Modifier.height(16.dp))

        SkipButton(onClick = {
            gameViewModel.skipWord()
            if(gameViewModel.getSoundEnabled()) {
                skip?.seekTo(0)
                skip?.start()
            }
        })

        Spacer(modifier = Modifier.weight(1f))

        Keyboard(
            modifier = modifier.fillMaxWidth(),
            onKey = { char ->

                when (char) {
                    '✓' -> {
                        gameViewModel.submitWord(
                            gameViewModel.getCurrentWord(),
                            gameViewModel.getCurrentLetters()
                        ) { isSuccessful ->  // Callback from submitWord
                            if (isSuccessful) {
                                if(gameViewModel.getSoundEnabled()) {
                                    valid?.seekTo(0)
                                    valid?.start()
                                }
                                Log.i("debug", "Word submitted successfully")
                                gameViewModel.setCurrentWord("")
                            } else{
                                if(gameViewModel.getSoundEnabled()) {
                                    invalid?.seekTo(0)
                                    invalid?.start()
                                }
                            }
                        }

                    }
                    '⌫' -> {
                        gameViewModel.removeLastCharacter()
                        if(gameViewModel.getSoundEnabled()) {
                            keyType?.seekTo(0)
                            keyType?.start()
                        }

                    }
                    else -> {
                        gameViewModel.addCharacter(char)
                        if(gameViewModel.getSoundEnabled()) {
                            keyType?.seekTo(0)
                            keyType?.start()
                        }
                    }
                }
            },
        )
        }

    }


@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(modifier = Modifier.fillMaxSize(),
        gameViewModel = GameViewModel())

}