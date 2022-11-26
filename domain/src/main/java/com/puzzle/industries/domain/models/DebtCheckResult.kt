package com.puzzle.industries.domain.models

import com.puzzle.industries.domain.constants.Months

data class DebtCheckResult (
    val willBeInDebt: Boolean,
    val amount: Double,
    val forMonth: Months,
    val forYear: Int
)