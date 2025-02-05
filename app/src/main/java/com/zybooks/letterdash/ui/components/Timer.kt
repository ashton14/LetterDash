package com.zybooks.letterdash.ui.components

import android.os.CountDownTimer
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalContext


@Composable
fun Timer(durationMillis: Long = 60000, onTimerEnd: () -> Unit = {}) {
    var timeLeft by remember { mutableStateOf(durationMillis) }
    val context = LocalContext.current // Get the context
    var timer: CountDownTimer? = null


    DisposableEffect(Unit) { // Use Unit as key to run once
        timer = object : CountDownTimer(timeLeft, 1000) {
            override fun onTick(millisUntilFinished: Long) {
                timeLeft = millisUntilFinished
            }

            override fun onFinish() {
                timeLeft = 0 // Ensure it shows 0:00
                onTimerEnd()
            }
        }.start()

        onDispose {
            timer?.cancel()
            timer = null
        }
    }


    val minutes = (timeLeft / 60000).toInt()
    val seconds = ((timeLeft % 60000) / 1000).toInt()
    val formattedTime = String.format("%02d:%02d", minutes, seconds)

    Text(text = if (timeLeft <= 0) "Time's up!" else formattedTime)
}