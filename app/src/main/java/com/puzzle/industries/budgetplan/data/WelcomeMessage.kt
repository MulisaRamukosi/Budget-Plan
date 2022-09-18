package com.puzzle.industries.budgetplan.data

import androidx.annotation.StringRes

data class WelcomeMessage(
    val vectorId: Int,
    @StringRes val titleId: Int,
    @StringRes val messageId: Int
)
