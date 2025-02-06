package com.zybooks.letterdash.ui.components

import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.unit.sp

@Composable
fun Keyboard(
    modifier: Modifier = Modifier,
    onKey: (char: Char) -> Unit,
) {
    val keys = listOf(
        'Q', 'W', 'E', 'R', 'T', 'Y', 'U', 'I', 'O', 'P',
        'A', 'S', 'D', 'F', 'G', 'H', 'J', 'K', 'L',
        '⌫', 'Z', 'X', 'C', 'V', 'B', 'N', 'M', '✓'
    )

    Box(
        modifier = Modifier
            .fillMaxWidth()
            .padding(6.dp)
    ) {
        Column(modifier) {
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.fillMaxWidth()
            ) {
                keys.take(10).forEach { key ->
                    KeyboardKey(text = key.toString(),
                        modifier = Modifier.weight(1f),
                        onClick = { onKey(key) })
                }
            }
            Spacer(Modifier.size(4.dp))
            Row(
                horizontalArrangement = Arrangement.spacedBy(4.dp),
                modifier = Modifier.padding(start = 8.dp)
            ) {
                keys.drop(10).take(9).forEach { key ->
                    KeyboardKey(text = key.toString(),
                        modifier = Modifier.weight(1f),
                        onClick = { onKey(key) })
                }
                Spacer(Modifier.size(4.dp))
            }
            Spacer(Modifier.size(4.dp))
            Row(horizontalArrangement = Arrangement.spacedBy(4.dp)) {
                var color: Color
                keys.drop(19).take(9).forEach { key ->
                    if (key == '⌫') {
                        color = Color.Red
                    } else if (key == '✓') {
                        color = Color(0xFF009539)

                    } else {
                        color = Color.DarkGray
                    }
                    KeyboardKey(text = key.toString(),
                        modifier = Modifier.weight(1f),
                        color = color,
                        onClick = { onKey(key) })
                }
            }

        }
    }
}

@Composable
private fun KeyboardKey(
    text: String,
    modifier: Modifier = Modifier,
    onClick: () -> Unit,
    color: Color = Color.DarkGray
) {
    Box(
        modifier = modifier
            .height(60.dp)
            .clip(RoundedCornerShape(2.dp))
            .background(color = color)
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center
    ) {
        Text(text = text, fontSize = 24.sp, color = Color.White)
    }
}

@Preview
@Composable
fun PreviewKeyboard(){
    Keyboard(onKey = {})
}
