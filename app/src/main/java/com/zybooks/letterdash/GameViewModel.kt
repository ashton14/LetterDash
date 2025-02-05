package com.zybooks.letterdash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import android.app.Application
import android.content.Context


class GameViewModel : ViewModel() {
    //private val sharedPrefs = getApplication<Application>().getSharedPreferences("GameData", Context.MODE_PRIVATE)
    private var score = MutableLiveData<Int>(0)
    private var highScore = MutableLiveData<Int>(0)

    fun updateHighScore(newScore: Int) : Unit {
        score.value = newScore
    }

    init{
        loadHighScore()
    }

    private fun loadHighScore() {
        //if (score > current high score){
        //highScore.value = score.value
        //}

    }

    fun getHighScore() : Int {
        return highScore.value!!
    }

    fun getScore() : Int {
        return score.value!!
    }

}