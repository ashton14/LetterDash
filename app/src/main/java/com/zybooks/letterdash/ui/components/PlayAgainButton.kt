package com.zybooks.letterdash.ui.components

import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
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
fun PlayAgainButton(
    modifier: Modifier = Modifier,
    onClick: () -> Unit = {}) {
    Button(
        onClick = onClick,
        modifier = modifier
            .height(100.dp)
            .width(240.dp),
        shape = RoundedCornerShape(40),
        colors = ButtonDefaults.buttonColors(
            containerColor = Color(0xff8322ff), // Purple background color
            contentColor = Color.White // White text color
        )
    ) {
        Text(
            text = "PLAY AGAIN",
            color = Color.White, // White text
            fontWeight = FontWeight.Bold, // Bold text
            fontSize = 42.sp, // Text size
            style = TextStyle(
                fontWeight = FontWeight.Bold,
                fontSize = 15.sp
            )
        )
    }
}

