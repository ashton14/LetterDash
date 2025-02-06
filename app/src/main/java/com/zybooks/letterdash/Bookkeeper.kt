package com.zybooks.letterdash

import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

class Bookkeeper() {

    fun isValidWord(word: String): Boolean {
        val baseUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/$word"
        try {
            val url = URL(baseUrl)
            val connection = url.openConnection() as HttpURLConnection
            connection.requestMethod = "GET"
            connection.connectTimeout = 5000
            connection.readTimeout = 5000
            connection.connect()

            val responseCode = connection.responseCode
            connection.disconnect()

            return when (responseCode) {
                200 -> true
                else -> false
            }
        } catch (e: Exception) {
            "Error: ${e.message}"
        }
        return false;
    }

    fun generateLetters(): List<String> {
        val threshold = 21;
        var totalPoints = 0;
        val letters = mutableListOf<String>();

        while(letters.size < 3){
            val letter = randomLetter()
            val points = Letter.getPointsForLetter(letter);
            if (totalPoints + points > threshold) {
                continue;
            }
            totalPoints += points;
            letters.add(letter.toString());
        }
        return letters;
    }

    private fun randomLetter(): Char {
        val randomNumber = Random.nextInt(1, 27)
        return ('A' + (randomNumber - 1))
    }

    fun containsLetters(word: String, letters: List<Char>): Boolean {
        for (letter in letters){
            if (!word.contains(letter)){
                return false
            }
        }
        return true
    }

    fun scoreWord(letters: List<Char>): Int {
        var score = 0
        for (letter in letters) {
            score += Letter.getPointsForLetter(letter)
        }
        return score
    }


}