package com.puzzle.industries.budgetplan.theme.factory

import com.puzzle.industries.budgetplan.theme.colorPicker.ColorPickerColors
import com.puzzle.industries.budgetplan.theme.colorPicker.ColorPickerSet
import com.puzzle.industries.budgetplan.theme.colorPicker.ComposeColorPickerColors
import com.puzzle.industries.budgetplan.theme.util.ColorPickerColorsType

object ColorPickerColorsFactory {

    fun getColorPickerColors(colorPickerColorsType: ColorPickerColorsType): ComposeColorPickerColors {
        val colors = ColorPickerColors.colors

        val colorsMap = colors.associate { set ->
            set.name to when (colorPickerColorsType) {
                ColorPickerColorsType.LIGHT -> set.light
                ColorPickerColorsType.DARK -> set.dark
            }
        }

        return ComposeColorPickerColors(
            colors = colorsMap
        )
    }

    fun getColorPickerSet(): List<ColorPickerSet> {
        return ColorPickerColors.colors
    }

}