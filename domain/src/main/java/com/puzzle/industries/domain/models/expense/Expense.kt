package com.puzzle.industries.domain.models.expense

import com.puzzle.industries.domain.constants.FrequencyType
import java.util.*

data class Expense(
    val id: UUID = UUID.randomUUID(),
    val expenseGroupId: UUID,
    val name: String,
    val amount: Double,
    val description: String,
    val frequencyType: FrequencyType,
    val frequencyWhen: String,
    val lastModifyDate: Date = Date()
)
