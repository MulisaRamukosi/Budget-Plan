package com.puzzle.industries.domain.models.expense

import com.puzzle.industries.domain.constants.Frequency
import java.util.*

data class Expense(
    val id: UUID = UUID.randomUUID(),
    val expenseGroupId: UUID,
    val name: String,
    val amount: Double,
    val frequency: Frequency
)
