package com.puzzle.industries.budgetplan.theme.factory

import com.puzzle.industries.budgetplan.theme.colorPalette.ColorPalette
import com.puzzle.industries.budgetplan.theme.colorPalette.DarkColorPalette
import com.puzzle.industries.budgetplan.theme.colorPalette.LightColorPalette
import com.puzzle.industries.budgetplan.theme.util.ColorPaletteType


object ColorPaletteFactory {

    fun getColorPalette(colorPaletteType: ColorPaletteType): ColorPalette {
        return when(colorPaletteType){
            ColorPaletteType.DARK -> DarkColorPalette()
            ColorPaletteType.LIGHT -> LightColorPalette()
        }
    }
}