package com.puzzle.industries.data.util.historyHelpers

import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.expense.ExpenseHistory

internal class ExpenseHistoryGenerator : HistoryGeneratorDelegate<Expense, ExpenseHistory> {
    override fun generateHistory(
        reason: String,
        action: Action,
        vararg entity: Expense
    ): Array<ExpenseHistory> =
        entity.map { ExpenseHistory(expense = it, reason = reason, action = action) }.toTypedArray()
}