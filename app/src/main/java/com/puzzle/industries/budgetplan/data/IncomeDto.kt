package com.puzzle.industries.budgetplan.data

data class IncomeDto(
    val id: Int,
    val frequency: String,
    val amount: Double,
    val title: String,
    val description: String
)