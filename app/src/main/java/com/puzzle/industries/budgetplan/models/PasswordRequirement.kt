package com.puzzle.industries.budgetplan.models

import androidx.annotation.StringRes

data class PasswordRequirement(
    @StringRes val stringId: Int,
    val formatArgs: Array<Any> = emptyArray()
) {
    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (javaClass != other?.javaClass) return false

        other as PasswordRequirement

        if (stringId != other.stringId) return false
        if (!formatArgs.contentEquals(other.formatArgs)) return false

        return true
    }

    override fun hashCode(): Int {
        var result = stringId
        result = 31 * result + formatArgs.contentHashCode()
        return result
    }
}
