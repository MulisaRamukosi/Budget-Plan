package com.puzzle.industries.budgetplan.theme.factory

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.puzzle.industries.budgetplan.theme.spacing.BaseSpacing
import com.puzzle.industries.budgetplan.theme.spacing.CompactSpacing
import com.puzzle.industries.budgetplan.theme.spacing.LargeSpacing

object SpacingFactory {

    fun getSpacing(windowWidthSizeClass: WindowWidthSizeClass) : BaseSpacing {
        val spacing =  when(windowWidthSizeClass){
            WindowWidthSizeClass.Compact -> CompactSpacing()
            else -> LargeSpacing()
        }

        return BaseSpacing(
            default = spacing.default,
            extraSmall = spacing.extraSmall,
            small = spacing.small,
            medium = spacing.medium,
            large = spacing.large
        )
    }



}

