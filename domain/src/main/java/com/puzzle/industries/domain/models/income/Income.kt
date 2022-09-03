package com.puzzle.industries.domain.models.income

import com.puzzle.industries.domain.constants.Frequency
import java.util.*

data class Income(
    val id: UUID = UUID.randomUUID(),
    val frequency: Frequency,
    val amount: Double,
    val title: String,
    val description: String,
    val lastModifyDate: Date = Date()
)