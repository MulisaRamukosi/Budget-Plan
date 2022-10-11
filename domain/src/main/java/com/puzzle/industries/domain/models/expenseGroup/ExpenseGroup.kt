package com.puzzle.industries.domain.models.expenseGroup

import java.util.*

data class ExpenseGroup(
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val colorId: String,
    val lastModifyDate: Date = Date()
)
