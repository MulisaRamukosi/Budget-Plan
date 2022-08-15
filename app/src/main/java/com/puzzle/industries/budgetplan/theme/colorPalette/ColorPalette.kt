package com.puzzle.industries.budgetplan.theme.colorPalette

import androidx.compose.ui.graphics.Color


open class ColorPalette(
    val primaryColor: Color,
    val primaryDarkColor: Color,
    val secondaryColor: Color,
    val secondaryDarkColor: Color,
    val primaryTextColor: Color,
    val secondaryTextColor: Color,
    val isLight: Boolean,
    val backgroundColor: Color,
    val surfaceColor: Color,
    val onBackgroundTextColor: Color,
    val onSurfaceTextColor: Color,
    var errorColor: Color = Color(0xFFB00020),
    var onErrorTextColor: Color = Color(0xffffffff)
)
