package com.zybooks.letterdash.ui.components

import android.annotation.SuppressLint
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Settings
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Icon
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.dp

class SettingsButton {

    @SuppressLint("NotConstructor")
    @Composable
    fun Content(
        modifier: Modifier = Modifier,
        onClick: () -> Unit = {}) {
        Button(
            onClick = onClick,
            modifier = modifier
                .height(100.dp)
                .width(100.dp),
            shape = RoundedCornerShape(37),
            colors = ButtonDefaults.buttonColors(
                containerColor = Color(0xff8322ff), // Purple background color
                contentColor = Color.White // White text color
            )
        ) {
            Icon(
                imageVector = Icons.Default.Settings,
                contentDescription = "Settings",
                modifier = Modifier
                    .size(70.dp)
            )
        }
    }
}