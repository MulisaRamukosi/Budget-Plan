package com.puzzle.industries.budgetplan.theme.colorPicker

import androidx.compose.runtime.*
import androidx.compose.ui.graphics.Color

@Stable
class ComposeColorPickerColors(
    colors: Map<String, Color>
) {
    var colors by mutableStateOf(colors, structuralEqualityPolicy())
        internal set

    fun copy(
        colors: Map<String, Color> = this.colors
    ): ComposeColorPickerColors = ComposeColorPickerColors(
        colors = colors
    )

    fun getColor(colorName: String): Color {
        if (colors.containsKey(colorName)){
            return colors[colorName]!!
        }

        return Color(0xFFFF9800)
    }

    override fun toString(): String {
        return "ColorPickerColors(" +
                "colors=$colors" +
                ")"
    }
}

internal fun ComposeColorPickerColors.updateColorPickerColors(other: ComposeColorPickerColors) {
    colors = other.colors
}

internal fun defaultColorPickerColors(
    colors: Map<String, Color> = mapOf("color0" to Color(0xFFF44336) )
): ComposeColorPickerColors = ComposeColorPickerColors(
    colors = colors
)

internal val LocalColorPickerColors = staticCompositionLocalOf { defaultColorPickerColors() }