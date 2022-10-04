package com.puzzle.industries.budgetplan.ext

import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass

fun WindowSizeClass.isInLandscapeView(): Boolean {
    return widthSizeClass == WindowWidthSizeClass.Expanded
            || heightSizeClass == WindowHeightSizeClass.Compact
}

fun WindowSizeClass.isInTabletLandscapeView(): Boolean {
    return widthSizeClass == WindowWidthSizeClass.Expanded && heightSizeClass == WindowHeightSizeClass.Medium
}