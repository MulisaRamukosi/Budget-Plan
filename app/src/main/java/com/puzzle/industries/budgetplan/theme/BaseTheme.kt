package com.puzzle.industries.budgetplan.theme

import android.os.Build
import androidx.compose.material3.ColorScheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.remember
import com.google.accompanist.systemuicontroller.rememberSystemUiController
import com.puzzle.industries.budgetplan.theme.colorPicker.ComposeColorPickerColors
import com.puzzle.industries.budgetplan.theme.colorPicker.LocalColorPickerColors
import com.puzzle.industries.budgetplan.theme.colorPicker.updateColorPickerColors
import com.puzzle.industries.budgetplan.theme.spacing.ComposeSpacing
import com.puzzle.industries.budgetplan.theme.spacing.LocalSpacing
import com.puzzle.industries.budgetplan.theme.spacing.updateSpacing

@Composable
fun BaseTheme(
    colorScheme: ColorScheme,
    typography: Typography,
    spacing: ComposeSpacing,
    colorPickerColors: ComposeColorPickerColors,
    content: @Composable () -> Unit
){
    val rememberSpacing = remember {
        spacing.copy()
    }.apply { updateSpacing(spacing) }

    val rememberColorPickerColors = remember {
        colorPickerColors.copy()
    }.apply {
        updateColorPickerColors(colorPickerColors)
    }

    when{
        Build.VERSION.SDK_INT < Build.VERSION_CODES.S -> {
            val systemUiController = rememberSystemUiController()
            systemUiController.setStatusBarColor(color = colorScheme.primary)
        }
    }

    CompositionLocalProvider(
        LocalSpacing provides rememberSpacing,
        LocalColorPickerColors provides rememberColorPickerColors
    ){
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content
        )
    }


}

val MaterialTheme.spacing: ComposeSpacing
    @Composable
    @ReadOnlyComposable
    get() = LocalSpacing.current

val MaterialTheme.colorPickerColors: ComposeColorPickerColors
    @Composable
    @ReadOnlyComposable
    get() = LocalColorPickerColors.current