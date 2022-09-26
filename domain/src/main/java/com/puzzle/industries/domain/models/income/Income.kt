package com.puzzle.industries.domain.models.income

import com.puzzle.industries.domain.constants.FrequencyType
import java.util.*

data class Income(
    val id: UUID = UUID.randomUUID(),
    val frequencyType: FrequencyType,
    val frequencyWhen: String,
    val amount: Double,
    val title: String,
    val description: String,
    val lastModifyDate: Date = Date()
)