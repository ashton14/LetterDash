package com.zybooks.letterdash

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

enum class Letter(val points: Int) {
    A(1), B(3), C(3), D(2), E(1),
    F(4), G(2), H(4), I(1), J(8),
    K(5), L(1), M(3), N(1), O(1),
    P(3), Q(10), R(1), S(1), T(1),
    U(1), V(4), W(4), X(8), Y(4),
    Z(10);

    companion object {
        fun getPointsForLetter(letter: Char): Int {
            return entries.find { it.name == letter.uppercase() }?.points
                ?: throw IllegalArgumentException("Invalid Character \"$letter\"")
        }
    }

}