package com.zybooks.letterdash.ui.components

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun DifficultyButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    text: String,
    color: Color
) {
    Button(
        onClick = onClick,
        modifier = modifier,
        shape = RoundedCornerShape(40),
        colors = ButtonDefaults.buttonColors(
            containerColor = color,
            contentColor = Color.White
        )
    ) {
        Text(
            text = text,
            color = Color.White,
            fontWeight = FontWeight.Bold,
            fontSize = 12.sp,
            style = TextStyle(
                fontWeight = FontWeight.Bold
            ),
            modifier = Modifier.padding(top = 0.dp, bottom = 0.dp)

        )
    }
}
