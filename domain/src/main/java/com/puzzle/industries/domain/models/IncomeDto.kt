package com.puzzle.industries.domain.models

import java.util.*

data class IncomeDto(
    val id: Int,
    val frequency: String,
    val amount: Double,
    val title: String,
    val description: String,
    val lastModifyDate: Date
    )