package com.zybooks.letterdash

import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel



class GameViewModel : ViewModel() {
    private val bookkeeper = Bookkeeper()

    private var score = MutableLiveData<Int>(0)
    private var highScore = MutableLiveData<Int>(0)
    private var currentLetters = MutableLiveData<List<Char>>(bookkeeper.generateLetters())


    fun updateHighScore() : Unit {
        if (score.value!! > highScore.value!!) {
            highScore.value = score.value
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

    fun getCurrentLetters() : List<Char> {
        return currentLetters.value!!
    }

    fun resetGame() : Unit {
        score.value = 0
        currentLetters.value = bookkeeper.generateLetters()
    }


    fun submitWord(word: String, letters: List<Char>): Boolean {
        if (!bookkeeper.isValidWord(word)) {
            //dialog saying invalid word
            return false
        }
        if (!bookkeeper.containsLetters(word, letters)) {
            //dialog saying missing letters
            return false
        }

        updateScore(bookkeeper.scoreWord(letters))
        updateCurrentLetters()
        println("Score: " + score.value)
        println("New letters: " + currentLetters.value)
        return true
    }


}