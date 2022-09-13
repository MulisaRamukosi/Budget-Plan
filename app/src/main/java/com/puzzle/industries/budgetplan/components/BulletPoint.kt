package com.puzzle.industries.budgetplan.components

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.size
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Composable
fun BulletPoint(color: Color, size: Dp = 4.dp) {
    Canvas(modifier = Modifier.size(size = size)) {
        drawCircle(color = color)
    }
}