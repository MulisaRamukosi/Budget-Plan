package com.puzzle.industries.budgetplan.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.theme.factory.ColorsFactory
import com.puzzle.industries.budgetplan.theme.factory.SpacingFactory
import com.puzzle.industries.budgetplan.theme.factory.TypographyFactory
import com.puzzle.industries.budgetplan.theme.util.ColorPaletteType
import com.puzzle.industries.budgetplan.theme.util.FontType
import com.puzzle.industries.data.compose.BudgetPlanMaterialTheme

@Composable
@ExperimentalMaterial3WindowSizeClassApi
fun BudgetPlanTheme(
    darkTheme: Boolean = isSystemInDarkTheme(),
    windowSizeClass: WindowSizeClass = WindowSizeClass.calculateFromSize(DpSize(width = 500.dp, height = 800.dp)),
    content: @Composable () -> Unit
) {

    val colorPaletteType: ColorPaletteType = if (darkTheme) {
        ColorPaletteType.DARK
    } else {
        ColorPaletteType.LIGHT
    }

    val colors = ColorsFactory.getTheme(colorPaletteType)
    val typography = TypographyFactory.getTypography(FontType.SOURCE_SANS_PRO)
    val spacing = SpacingFactory.getSpacing(windowSizeClass.widthSizeClass)

    BudgetPlanMaterialTheme(
        colors = colors,
        typography = typography,
        shapes = Shapes,
        spacing = spacing,
        content = content
    )


}


