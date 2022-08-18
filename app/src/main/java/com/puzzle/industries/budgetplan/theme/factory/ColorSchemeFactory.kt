package com.puzzle.industries.budgetplan.theme.factory

import androidx.compose.material3.ColorScheme
import com.puzzle.industries.budgetplan.theme.util.ColorPaletteType

object ColorSchemeFactory {

    fun getColorScheme(colorPaletteType: ColorPaletteType) : ColorScheme {
        val colorPalette = ColorPaletteFactory.getColorPalette(colorPaletteType = colorPaletteType)
        return ColorScheme(
            primary = colorPalette.primary,
            onPrimary = colorPalette.onPrimary,
            primaryContainer = colorPalette.primaryContainer,
            onPrimaryContainer = colorPalette.onPrimaryContainer,
            inversePrimary = colorPalette.inversePrimary,
            secondary = colorPalette.secondary,
            onSecondary = colorPalette.onSecondary,
            secondaryContainer = colorPalette.secondaryContainer,
            onSecondaryContainer = colorPalette.onSecondaryContainer,
            tertiary = colorPalette.tertiary,
            onTertiary = colorPalette.onTertiary,
            tertiaryContainer = colorPalette.tertiaryContainer,
            onTertiaryContainer = colorPalette.onTertiaryContainer,
            background = colorPalette.background,
            onBackground = colorPalette.onBackground,
            surface = colorPalette.surface,
            onSurface = colorPalette.onSurface,
            surfaceVariant = colorPalette.surfaceVariant,
            onSurfaceVariant = colorPalette.onSurfaceVariant,
            inverseSurface = colorPalette.inverseSurface,
            inverseOnSurface = colorPalette.inverseOnSurface,
            error = colorPalette.error,
            onError = colorPalette.onError,
            errorContainer = colorPalette.errorContainer,
            onErrorContainer = colorPalette.onErrorContainer,
            outline = colorPalette.outline,
            surfaceTint = colorPalette.surfaceTint,
            outlineVariant = colorPalette.outline,
            scrim = colorPalette.shadow
        )
    }
}