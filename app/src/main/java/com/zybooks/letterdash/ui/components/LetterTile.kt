package com.zybooks.letterdash.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
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