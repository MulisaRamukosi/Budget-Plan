package com.puzzle.industries.budgetplan.theme.factory

import com.puzzle.industries.budgetplan.theme.spacing.ComposeSpacing
import com.puzzle.industries.budgetplan.theme.spacing.CompactSpacing

object SpacingFactory {

    fun getSpacing() : ComposeSpacing {
        val spacing =  CompactSpacing()

        return ComposeSpacing(
            default = spacing.default,
            extraSmall = spacing.extraSmall,
            small = spacing.small,
            medium = spacing.medium,
            large = spacing.large
        )
    }



}

