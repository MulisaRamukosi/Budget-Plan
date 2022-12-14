package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.income.Income

interface MonthTotalAmountCalculatorService {
    fun calculateTotalIncomesForMonth(
        month: Months? = null,
        year: Int? = null,
        incomes: List<Income>
    ): Double

    fun calculateTotalExpensesForMonth(
        month: Months? = null,
        year: Int? = null,
        expenses: List<Expense>
    ): Double

    fun calculatePayableExpenseInAMonth(expense: Expense): Double
}