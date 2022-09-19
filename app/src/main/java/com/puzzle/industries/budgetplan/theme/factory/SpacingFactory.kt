package com.puzzle.industries.budgetplan.theme.factory

import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import com.puzzle.industries.budgetplan.theme.spacing.BaseSpacing
import com.puzzle.industries.budgetplan.theme.spacing.CompactSpacing

object SpacingFactory {

    fun getSpacing() : BaseSpacing {
        val spacing =  CompactSpacing()

        return BaseSpacing(
            default = spacing.default,
            extraSmall = spacing.extraSmall,
            small = spacing.small,
            medium = spacing.medium,
            large = spacing.large
        )
    }



}

