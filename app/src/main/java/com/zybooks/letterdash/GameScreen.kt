package com.zybooks.letterdash

import androidx.annotation.OptIn
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi
import com.zybooks.letterdash.ui.components.Keyboard
import com.zybooks.letterdash.ui.components.LetterTile
import com.zybooks.letterdash.ui.components.SkipButton
import com.zybooks.letterdash.ui.components.Timer


@OptIn(UnstableApi::class)
@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    onTimerEnd: () -> Unit = {},
    gameViewModel: GameViewModel
) {

    val focusRequester = remember { FocusRequester() }
    gameViewModel.currentWord.observeAsState("").value

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
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
                .padding(16.dp) // Add some padding around the timer
                .align(Alignment.CenterHorizontally),
            contentAlignment = Alignment.Center

        ) {
            Timer(onTimerEnd = onTimerEnd)
        }

        Text(
            modifier = modifier.padding(16.dp)
                .focusRequester(focusRequester),
            text = "SCORE: ${gameViewModel.getScore()}",
            fontSize = 30.sp,

        )

        // Text Field
        OutlinedTextField(
            value = gameViewModel.getCurrentWord(),
            onValueChange = { gameViewModel.setCurrentWord(it)},
            keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Text),
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp)
                .focusRequester(focusRequester)
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
            Log.i("debug", gameViewModel.getCurrentLetters().toString())
            gameViewModel.skipWord()
            Log.i("debug", gameViewModel.getCurrentLetters().toString())
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
                                Log.i("debug", "Word submitted successfully")
                                gameViewModel.setCurrentWord("")
                            }
                        }

                    }
                    '⌫' -> {
                        gameViewModel.removeLastCharacter()

                    }
                    else -> {
                        gameViewModel.addCharacter(char)
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