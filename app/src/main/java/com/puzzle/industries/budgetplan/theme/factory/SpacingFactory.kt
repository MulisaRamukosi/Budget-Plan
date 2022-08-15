package com.puzzle.industries.budgetplan.theme.factory

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.puzzle.industries.budgetplan.theme.spacing.CompactSpacing
import com.puzzle.industries.budgetplan.theme.spacing.LargeSpacing
import com.puzzle.industries.data.compose.spacing.Spacing

object SpacingFactory {

    fun getSpacing(windowWidthSizeClass: WindowWidthSizeClass) : Spacing {
        val spacing =  when(windowWidthSizeClass){
            WindowWidthSizeClass.Compact -> CompactSpacing()
            else -> LargeSpacing()
        }

        return Spacing(
            default = spacing.default,
            extraSmall = spacing.extraSmall,
            small = spacing.small,
            medium = spacing.medium,
            large = spacing.large
        )
    }



}

