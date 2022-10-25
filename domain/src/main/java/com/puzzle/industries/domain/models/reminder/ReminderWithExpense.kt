package com.puzzle.industries.domain.models.reminder

import com.puzzle.industries.domain.models.expense.Expense

data class ReminderWithExpense(
    val reminder: Reminder,
    val expense: Expense
)