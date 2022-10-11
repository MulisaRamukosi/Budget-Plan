package com.puzzle.industries.budgetplan.theme.spacing

import androidx.compose.runtime.*
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

@Stable
class ComposeSpacing(
    default: Dp,
    extraSmall: Dp,
    small: Dp,
    medium: Dp,
    large: Dp
) {

    var default by mutableStateOf(default, structuralEqualityPolicy())
        internal set
    var extraSmall by mutableStateOf(extraSmall, structuralEqualityPolicy())
        internal set
    var small by mutableStateOf(small, structuralEqualityPolicy())
        internal set
    var medium by mutableStateOf(medium, structuralEqualityPolicy())
        internal set
    var large by mutableStateOf(large, structuralEqualityPolicy())
        internal set

    fun copy(
        default: Dp = this.default,
        extraSmall: Dp = this.extraSmall,
        small: Dp = this.small,
        medium: Dp = this.medium,
        large: Dp = this.large
    ): ComposeSpacing = ComposeSpacing(
        default = default,
        extraSmall = extraSmall,
        small = small,
        medium = medium,
        large = large
    )

    override fun toString(): String {
        return "Spacing(" +
                "default=$default, " +
                "extraSmall=$extraSmall, " +
                "small=$small, " +
                "medium=$medium, " +
                "large=$medium" +
                ")"
    }
}

internal fun ComposeSpacing.updateSpacing(other: ComposeSpacing) {
    default = other.default
    extraSmall = other.extraSmall
    small = other.small
    medium = other.medium
    large = other.large
}

internal fun defaultSpacing(
    default: Dp = 0.dp,
    extraSmall: Dp = 2.dp,
    small: Dp = 4.dp,
    medium: Dp = 8.dp,
    large: Dp = 16.dp
): ComposeSpacing = ComposeSpacing(
    default = default,
    extraSmall = extraSmall,
    small = small,
    medium = medium,
    large = large
)


internal val LocalSpacing = staticCompositionLocalOf { defaultSpacing() }