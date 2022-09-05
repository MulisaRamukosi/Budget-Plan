package com.puzzle.industries.data.util.historyHelpers

import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupHistory

internal class ExpenseGroupHistoryGenerator :
    HistoryGeneratorDelegate<ExpenseGroup, ExpenseGroupHistory> {
    override fun generateHistory(
        reason: String,
        action: Action,
        vararg entity: ExpenseGroup
    ): Array<ExpenseGroupHistory> =
        entity.map { ExpenseGroupHistory(expenseGroup = it, action = action, reason = reason) }
            .toTypedArray()
}