package com.puzzle.industries.data.compose

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Shapes
import androidx.compose.material3.Typography
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
    colorScheme: ColorScheme,
    typography: Typography,
    spacing: Spacing,
    content: @Composable () -> Unit
){
    val rememberSpacing = remember {
        spacing.copy()
    }.apply { updateSpacing(spacing) }


    when{
        Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(color = colorScheme.primary)
        }
    }

    CompositionLocalProvider(LocalSpacing provides rememberSpacing){
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }
}

val MaterialTheme.spacing: Spacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current
