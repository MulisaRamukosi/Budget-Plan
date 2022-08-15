package com.puzzle.industries.budgetplan.theme.factory

import androidx.compose.material.Typography
import androidx.compose.ui.text.font.Font
import androidx.compose.ui.text.font.FontFamily
import androidx.compose.ui.text.font.FontWeight
import com.puzzle.industries.budgetplan.theme.typography.SourceSansPro
import com.puzzle.industries.budgetplan.theme.util.FontType

object TypographyFactory {

    fun getTypography(fontType: FontType) : Typography {
        val baseFont =  when(fontType) {
            FontType.SOURCE_SANS_PRO -> SourceSansPro()
        }

        val fontFamily = FontFamily(
            Font(
                resId = baseFont.regular,
                weight = FontWeight.Normal
            ),
            Font(
                resId = baseFont.medium,
                weight = FontWeight.Medium
            ),
            Font(
                resId = baseFont.light,
                weight = FontWeight.Light
            ),
            Font(
                resId = baseFont.bold,
                weight = FontWeight.Bold
            )
        )

        return Typography(defaultFontFamily = fontFamily)
    }

}