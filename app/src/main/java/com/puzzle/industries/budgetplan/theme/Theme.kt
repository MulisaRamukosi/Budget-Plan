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
import androidx.core.view.ViewCompat
import com.puzzle.industries.budgetplan.theme.factory.ColorSchemeFactory
import com.puzzle.industries.budgetplan.theme.factory.SpacingFactory
import com.puzzle.industries.budgetplan.theme.factory.TypographyFactory
import com.puzzle.industries.budgetplan.theme.util.ColorPaletteType
import com.puzzle.industries.budgetplan.theme.util.FontType

@Composable
@ExperimentalMaterial3WindowSizeClassApi
fun BudgetPlanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    dynamicColor: Boolean = true,
    windowSizeClass: WindowSizeClass = WindowSizeClass.calculateFromSize(
        DpSize(
            width = 500.dp,
            height = 800.dp
        )
    ),
    content: @Composable () -> Unit
) {

    val colorPaletteType: ColorPaletteType =
        if (darkTheme) ColorPaletteType.DARK else ColorPaletteType.LIGHT
    val typography = TypographyFactory.getTypography(FontType.SOURCE_SANS_PRO)
    val spacing = SpacingFactory.getSpacing(windowSizeClass.widthSizeClass)

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
            (view.context as Activity).window.statusBarColor = colorScheme.primary.toArgb()
            ViewCompat.getWindowInsetsController(view)?.isAppearanceLightStatusBars = darkTheme
        }
    }

    BaseTheme(
        colorScheme = colorScheme,
        typography = typography,
        spacing = spacing,
        content = content
    )


}


