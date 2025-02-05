package com.zybooks.letterdash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class GameViewModel : ViewModel() {
    private val bookkeeper = Bookkeeper()

    private var score = MutableLiveData<Int>(0)
    private var highScore = MutableLiveData<Int>(0)
    private var currentLetters = MutableLiveData<List<String>>(bookkeeper.generateLetters())


    fun updateHighScore(newScore: Int) : Unit {
        if (newScore > highScore.value!!) {
            score.value = newScore
            }
    }

    fun updateScore(points: Int) : Unit {
        score.value = score.value?.plus(points)
    }

    fun updateCurrentLetters() : Unit {
        currentLetters.value = bookkeeper.generateLetters()
    }


    fun getHighScore() : Int {
        return highScore.value!!
    }

    fun getScore() : Int {
        return score.value!!
    }

    fun getCurrentLetters() : List<String> {
        return currentLetters.value!!
    }


}