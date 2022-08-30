package com.puzzle.industries.domain.models.expenseGroup

import java.util.*

data class ExpenseGroup(
    val id: UUID,
    val name: String,
    val lastModifyDate: Date
)
