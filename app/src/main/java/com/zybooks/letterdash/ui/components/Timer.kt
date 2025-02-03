package com.zybooks.letterdash.ui.components

import android.os.CountDownTimer
import android.widget.TextView

class Timer(private val textView: TextView, private val durationMillis: Long = 60000, private val intervalMillis: Long = 1000) {
    private var countDownTimer: CountDownTimer? = null

    fun start() {
        countDownTimer = object : CountDownTimer(durationMillis, intervalMillis) {
            override fun onTick(millisUntilFinished: Long) {
                textView.text = "${(millisUntilFinished / 1000).toInt()}:${millisUntilFinished / 1000}"
            }

            override fun onFinish() {
                textView.text = "Time's up!"
            }
        }.start()
    }

    fun cancel() {
        countDownTimer?.cancel()
    }

}
