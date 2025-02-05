package com.zybooks.letterdash

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Brush
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.viewinterop.AndroidView
import com.zybooks.letterdash.ui.components.Keyboard

@Composable
fun GameScreen(modifier: Modifier = Modifier,
               score: Int,
               onTimerEnd: () -> Unit = {}) {

    val context = LocalContext.current
    var currentWord by remember { mutableStateOf("") }
    val keyboard = remember { mutableStateOf<Keyboard?>(null) }

    Column(modifier = modifier
        .fillMaxSize()
        .background(
            Brush.verticalGradient(
                colors = listOf(Color.White, Color(0xff8ecdff)),
                startY = 0f,
                endY = Float.POSITIVE_INFINITY
            )
        ),
        //contentAlignment = Alignment.Center
    ) {  // Fill the screen
        Box(modifier = Modifier.fillMaxWidth()) { // Timer
            // Your timer composable here
        }
        Box(modifier = Modifier.fillMaxWidth()) { // Input
            // Your input display composable here (e.g., Text(currentWord))
        }
        Row(modifier = Modifier.fillMaxWidth()) { // Letters
            // Your letter display composables here
        }

        Box( // Box to contain and align the keyboard
            modifier = Modifier
                .fillMaxWidth()
                .weight(1f) // Take up remaining space, pushing keyboard to bottom
        ) {
            AndroidView(
                factory = {
                    Keyboard(it).also {
                        keyboard.value = it
                    }
                },
                modifier = Modifier
                    .fillMaxWidth()
                    .wrapContentHeight()
                    .align(Alignment.BottomCenter) // Correct alignment within the Box
            )
        }
    }

    LaunchedEffect(keyboard.value) { // Set listener ONCE
        keyboard.value?.setOnKeyboardActionListener(object : Keyboard.OnKeyboardActionListener {
            override fun onKeyClicked(key: String) {
                when (key) {
                    "ENTER" -> {
                        // Game logic (check word)
                    }
                    "⌫" -> {
                        if (currentWord.isNotEmpty()) {
                            currentWord = currentWord.substring(0, currentWord.length - 1)
                        }
                    }
                    else -> {
                        currentWord += key
                    }
                }
            }
        })
    }
}

@Preview
@Composable
fun GameScreenPreview() {
    GameScreen(modifier = Modifier.fillMaxSize(), 0)

}