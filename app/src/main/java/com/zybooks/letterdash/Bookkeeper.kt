package com.zybooks.letterdash

import android.util.Log
import java.net.ConnectException
import java.net.HttpURLConnection
import java.net.SocketTimeoutException
import java.net.URL
import kotlin.random.Random
import kotlinx.coroutines.*
import java.io.IOException


class Bookkeeper() {

    fun isValidWord(word: String, callback: (Boolean) -> Unit) {  // Add a callback
        CoroutineScope(Dispatchers.IO).launch { // Launch in IO dispatcher
            val baseUrl = "https://api.dictionaryapi.dev/api/v2/entries/en/$word"
            try {
                val url = URL(baseUrl)
                val connection = url.openConnection() as HttpURLConnection
                connection.requestMethod = "GET"
                connection.connectTimeout = 5000
                connection.readTimeout = 5000
                Log.i("debug", "connecting")

                connection.connect()
                Log.i("debug", "connected")

                val responseCode = connection.responseCode
                connection.disconnect()
                Log.i("debug", "response code: $responseCode")

                withContext(Dispatchers.Main) { // Switch back to main thread
                    callback(responseCode == 200) // Call the callback
                }


            } catch (e: ConnectException) {
                Log.e("debug", "Connection Error: ${e.message}")
                withContext(Dispatchers.Main) { callback(false) }
            } catch (e: SocketTimeoutException) {
                Log.e("debug", "Timeout Error: ${e.message}")
                withContext(Dispatchers.Main) { callback(false) }
            } catch (e: IOException) {
                Log.e("debug", "IO Exception: ${e.message}")
                e.printStackTrace()
                withContext(Dispatchers.Main) { callback(false) }
            } catch (e: Exception) {
                Log.e("debug", "General Error: ${e.message}")
                e.printStackTrace()
                withContext(Dispatchers.Main) { callback(false) }
            }
        }
    }

    fun generateLetters(): List<Char> {
        val threshold = 18;
        var totalPoints = 0;
        val letters = mutableListOf<Char>();

        while(letters.size < 3){
            var letter = randomLetter()
            while (letters.contains(letter)){
                letter = randomLetter()
            }
            val points = Letter.getPointsForLetter(letter);
            if (totalPoints + points > threshold) {
                continue;
            }
            totalPoints += points;
            letters.add(letter);
        }
        return letters;
    }

    private fun randomLetter(): Char {
        val randomNumber = Random.nextInt(1, 27)
        return ('A' + (randomNumber - 1))
    }

    fun containsLetters(word: String, letters: List<Char>): Boolean {
        for (letter in letters){
            if (!word.contains(letter.toString().lowercase())){
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