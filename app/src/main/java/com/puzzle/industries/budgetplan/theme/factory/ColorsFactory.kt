package com.puzzle.industries.budgetplan.theme.factory

import androidx.compose.material.Colors
import com.puzzle.industries.budgetplan.theme.util.ColorPaletteType

object ColorsFactory {

    fun getTheme(colorPaletteType: ColorPaletteType) : Colors {
        val colorPalette = ColorPaletteFactory.getColorPalette(colorPaletteType = colorPaletteType)
        return Colors(
            primary = colorPalette.primaryColor,
            primaryVariant = colorPalette.primaryDarkColor,
            secondary = colorPalette.secondaryColor,
            secondaryVariant = colorPalette.secondaryDarkColor,
            background = colorPalette.backgroundColor,
            surface = colorPalette.surfaceColor,
            error = colorPalette.errorColor,
            onPrimary = colorPalette.primaryTextColor,
            onSecondary =  colorPalette.secondaryTextColor,
            onBackground = colorPalette.onBackgroundTextColor,
            onSurface = colorPalette.onSurfaceTextColor,
            onError =  colorPalette.onErrorTextColor,
            isLight = colorPalette.isLight
        )
    }
}