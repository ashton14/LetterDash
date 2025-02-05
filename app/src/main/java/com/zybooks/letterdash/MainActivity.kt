package com.zybooks.letterdash

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp
import java.net.HttpURLConnection
import java.net.URL
import kotlin.random.Random

class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()
        setContent {
            App()
        }
    }
}


fun IsValidWord(word: String): Boolean {
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

fun GenerateLetters(): List<String> {
    val threshold = 21;
    var totalPoints = 0;
    var letters = mutableListOf<String>();

    while(letters.size < 3){
        var letter = RandomLetter()
        var points = Letter.getPointsForLetter(letter);
        if (totalPoints + points > threshold) {
            continue;
        }
        totalPoints += points;
        letters.add(letter.toString());
    }
    return letters;
}

fun RandomLetter(): Char {
    val randomNumber = Random.nextInt(1, 27)
    return ('A' + (randomNumber - 1))
}


@Composable
fun LetterTile(
    letter: String,
    modifier: Modifier = Modifier,
    fontSize: Int = 36
) {
    Box(
        modifier = modifier
            .size(60.dp)
            .background(
                color = Color(0xFFD4A24B),
                shape = RoundedCornerShape(8.dp)
            )
            .border(2.dp, Color.Black, RoundedCornerShape(8.dp))
            .padding(0.dp)

    ) {
        Text(
            text = letter,
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                color = Color.Black,
                fontSize = fontSize.sp
            ),
            modifier = Modifier.align(Alignment.Center)
        )
    }
}
