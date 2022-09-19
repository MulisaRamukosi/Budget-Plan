package com.puzzle.industries.budgetplan.theme

import android.app.Activity
import android.os.Build
import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.dynamicDarkColorScheme
import androidx.compose.material3.dynamicLightColorScheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.SideEffect
import androidx.compose.ui.graphics.toArgb
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.platform.LocalView
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.core.view.WindowCompat
import com.puzzle.industries.budgetplan.theme.factory.ColorSchemeFactory
import com.puzzle.industries.budgetplan.theme.factory.SpacingFactory
import com.puzzle.industries.budgetplan.theme.factory.TypographyFactory
import com.puzzle.industries.budgetplan.theme.util.ColorPaletteType
import com.puzzle.industries.budgetplan.theme.util.FontType

@Composable
fun BudgetPlanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = false,
    content: @Composable () -> Unit
) {

    val colorPaletteType: ColorPaletteType =
        if (darkTheme) ColorPaletteType.DARK else ColorPaletteType.LIGHT
    val typography = TypographyFactory.getTypography(FontType.MONTSERRAT)
    val spacing = SpacingFactory.getSpacing()

    val colorScheme = when {
        dynamicColor && Build.VERSION.SDK_INT >= Build.VERSION_CODES.S -> {
            val context = LocalContext.current
            if (darkTheme) dynamicDarkColorScheme(context) else dynamicLightColorScheme(context)
        }
        else -> ColorSchemeFactory.getColorScheme(colorPaletteType)
    }
    val view = LocalView.current
    if (!view.isInEditMode) {
        SideEffect {
            val window = (view.context as Activity).window
            window.statusBarColor = colorScheme.primary.toArgb()
            WindowCompat.getInsetsController(window, view).isAppearanceLightStatusBars = darkTheme
        }
    }

    BaseTheme(
        colorScheme = colorScheme,
        typography = typography,
        spacing = spacing,
        content = content
    )


}


