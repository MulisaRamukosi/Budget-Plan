package com.puzzle.industries.domain.models.expenseGroup

import com.puzzle.industries.domain.models.expense.Expense

data class ExpenseGroupWithExpenses(
    val expenseGroup: ExpenseGroup,
    val expenses: List<Expense>
)
