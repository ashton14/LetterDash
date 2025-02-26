package com.zybooks.letterdash

import androidx.annotation.OptIn
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.media3.common.util.Log
import androidx.media3.common.util.UnstableApi


class GameViewModel : ViewModel() {
    private val bookkeeper = Bookkeeper(this)

    private var score = MutableLiveData<Int>(0)
    private var highScore = MutableLiveData<Int>(0)
    private var currentLetters = MutableLiveData<List<Char>>()
    var currentWord = MutableLiveData<String>("")
    var difficulty = MutableLiveData<Difficulty>(Difficulty.EASY)
    var soundEnabled = MutableLiveData<Boolean>(true)


    fun updateHighScore() {
        if (score.value != null && highScore.value != null && score.value!! > highScore.value!!) {
            highScore.value = score.value
        }
    }

    private fun updateScore(points: Int) {
        if((score.value ?: 0) + points < 0) {
            score.value = 0
        } else {
            score.value = (score.value ?: 0) + points
        }
    }

    private fun updateCurrentLetters() {
        currentLetters.value = bookkeeper.generateLetters()
    }

    fun getHighScore(): Int {
        return highScore.value ?: 0
    }

    fun getScore(): Int {
        return score.value ?: 0
    }

    fun getCurrentLetters(): List<Char> {
        return currentLetters.value ?: emptyList()
    }

    fun setCurrentWord(newWord: String) {
        currentWord.value = newWord
    }

    fun getCurrentWord(): String {
        return currentWord.value ?: ""
    }

    fun removeLastCharacter() {
        if (currentWord.value?.isNotEmpty() == true){
            currentWord.value = currentWord.value!!.dropLast(1)
        }
    }

    fun addCharacter(char: Char) {
        currentWord.value += char
    }

    fun resetGame() {
        score.value = 0
        currentLetters.value = bookkeeper.generateLetters()
    }

    fun setDifficulty(newDifficulty: Difficulty) {
        difficulty.value = newDifficulty
    }

    fun getDifficulty(): Difficulty {
        return difficulty.value ?: Difficulty.EASY
    }

    fun setSoundEnabled(enabled: Boolean) {
        soundEnabled.value = enabled
    }

    fun getSoundEnabled(): Boolean {
        return soundEnabled.value ?: true
    }

    fun setHighScore(newHighScore: Int) {
        highScore.value = newHighScore
    }

    fun isSoundEnabled(): Boolean {
        return soundEnabled.value ?: true
    }

    fun skipWord() {
        currentWord.value = " "
        updateCurrentLetters()
        updateScore(-15)
        currentWord.value = ""
    }


    @OptIn(UnstableApi::class)
    fun submitWord(word: String, letters: List<Char>, callback: (Boolean) -> Unit) {

        bookkeeper.isValidWord(word) { isValid ->  // Callback from isValidWord
            if (!isValid) {
                Log.i("debug", "word is NOT valid")
                callback(false) // Call callback with false (invalid word)
                return@isValidWord // Important: Return from the lambda
            }

            Log.i("debug", "word is valid")

            if (!bookkeeper.containsLetters(word, letters)) {
                Log.i("debug", "word does NOT contain letters")
                callback(false) // Call callback with false (missing letters)
                return@isValidWord // Important: Return from the lambda
            }

            Log.i("debug", "word contains letters")
            updateScore(bookkeeper.scoreWord(letters))
            updateCurrentLetters()
            callback(true) // Call callback with true (word submitted successfully)
        }
    }


}