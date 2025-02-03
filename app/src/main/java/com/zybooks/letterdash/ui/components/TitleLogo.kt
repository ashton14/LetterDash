package com.zybooks.letterdash.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp
import com.zybooks.letterdash.LetterTile

@SuppressLint("NotConstructor")
class TitleLogo {

    @Composable
    fun Content(modifier: Modifier = Modifier){
        val letters = listOf("L", "E", "T", "T", "E", "R", "D", "A", "S", "H")
        Row (horizontalArrangement = Arrangement.SpaceEvenly,
            modifier = modifier
                .width(320.dp)
                .height(250.dp)
        )
        {
            for (letter in letters) {
                LetterTile(
                    letter = letter,
                    modifier = modifier
                        .size(32.dp)
                        .background(
                            color = Color(0xFFD4A24B),
                            shape = RoundedCornerShape(8.dp)
                        ),
                    fontSize = 18)
            }
        }
    }
}