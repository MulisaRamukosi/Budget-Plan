package com.puzzle.industries.data.compose

import androidx.compose.material.Colors
import androidx.compose.material.MaterialTheme
import androidx.compose.material.Shapes
import androidx.compose.material.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.puzzle.industries.data.compose.spacing.LocalSpacing
import com.puzzle.industries.data.compose.spacing.Spacing
import com.puzzle.industries.data.compose.spacing.updateSpacing

@Composable
fun BudgetPlanMaterialTheme(
    colors: Colors,
    typography: Typography,
    shapes: Shapes,
    spacing: Spacing,
    content: @Composable () -> Unit
){
    val systemUiController = rememberSystemUiController()

    val rememberSpacing = remember {
        spacing.copy()
    }.apply { updateSpacing(spacing) }



    systemUiController.setStatusBarColor(color = colors.primaryVariant)

    CompositionLocalProvider(LocalSpacing provides rememberSpacing){
        MaterialTheme(
            colors = colors,
            typography = typography,
            shapes = shapes,
            content = content
        )
    }
}

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
