package com.zybooks.letterdash

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.focus.FocusRequester
import androidx.compose.ui.focus.focusRequester
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.platform.LocalSoftwareKeyboardController
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.text.input.TextFieldValue
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import androidx.compose.ui.viewinterop.AndroidView
import com.zybooks.letterdash.ui.components.Keyboard
import com.zybooks.letterdash.ui.components.LetterTile
import com.zybooks.letterdash.ui.components.Timer


@Composable
fun GameScreen(
    modifier: Modifier = Modifier,
    onTimerEnd: () -> Unit = {},
    gameViewModel: GameViewModel
) {
    var currentWord by remember { mutableStateOf(TextFieldValue("")) }
    val focusRequester = remember { FocusRequester() }
    val keyboardController = LocalSoftwareKeyboardController.current

    LaunchedEffect(Unit) {
        focusRequester.requestFocus()
        keyboardController?.show()
    }
    //for keyboard
    //val context = LocalContext.current
    //val keyboard = remember { mutableStateOf<Keyboard?>(null) }

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
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        // Timer
        Box(
            modifier = Modifier
                .fillMaxWidth()
                .padding(16.dp) // Add some padding around the timer
                .align(Alignment.CenterHorizontally)

        ) {
            Timer(onTimerEnd = onTimerEnd)
        }

        Text(
            modifier = modifier.padding(16.dp)
                .focusRequester(focusRequester),
            text = "SCORE: ${gameViewModel.getScore()}",
            fontSize = 30.sp

        )

        // Text Field
        OutlinedTextField(
            value = currentWord,
            onValueChange = { currentWord = it },
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
        }

    }

//        Box( // Box to contain and align the keyboard
//            modifier = Modifier
//                .fillMaxWidth()
//                .weight(1f) // Take up remaining space, pushing keyboard to bottom
//        ) {
//            AndroidView(
//                factory = {
//                    Keyboard(it).also {
//                        keyboard.value = it
//                    }
//                },
//                modifier = Modifier
//                    .fillMaxWidth()
//                    .wrapContentHeight()
//                    .align(Alignment.BottomCenter) // Correct alignment within the Box
//            )
//        }
//    }
//
//    LaunchedEffect(keyboard.value) { // Set listener ONCE
//        keyboard.value?.setOnKeyboardActionListener(object : Keyboard.OnKeyboardActionListener {
//            override fun onKeyClicked(key: String) {
//                when (key) {
//                    "ENTER" -> {
//                        // Game logic (check word)
//                    }
//                    "⌫" -> {
//                        if (currentWord.isNotEmpty()) {
//                            currentWord = currentWord.substring(0, currentWord.length - 1)
//                        }
//                    }
//                    else -> {
//                        currentWord += key
//                    }
//                }
//            }
//        })
//    }


@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(modifier = Modifier.fillMaxSize(),
        gameViewModel = GameViewModel())

}