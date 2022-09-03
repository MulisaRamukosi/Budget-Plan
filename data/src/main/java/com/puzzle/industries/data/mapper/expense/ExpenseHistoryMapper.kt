package com.puzzle.industries.data.mapper.expense

import com.puzzle.industries.data.database.entity.expense.ExpenseHistoryEntity
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.expense.ExpenseHistory
import java.util.*

internal class ExpenseHistoryMapper {

    fun toExpenseHistoryEntity(expenseHistory: ExpenseHistory): ExpenseHistoryEntity {
        return ExpenseHistoryEntity(
            id = expenseHistory.id,
            expenseGroupId = expenseHistory.expenseGroupId,
            oldName = expenseHistory.oldName,
            newName = expenseHistory.newName,
            oldAmount = expenseHistory.oldAmount,
            newAmount = expenseHistory.newAmount,
            oldFrequency = expenseHistory.oldFrequency.name,
            newFrequency = expenseHistory.newFrequency.name,
            action = expenseHistory.action.name,
            reason = expenseHistory.reason
        )
    }

    fun toExpenseHistory(expenseHistory: ExpenseHistoryEntity): ExpenseHistory {
        return ExpenseHistory(
            id = expenseHistory.id,
            expenseGroupId = expenseHistory.expenseGroupId,
            oldName = expenseHistory.oldName,
            newName = expenseHistory.newName,
            oldAmount = expenseHistory.oldAmount,
            newAmount = expenseHistory.newAmount,
            oldFrequency = Frequency.valueOf(expenseHistory.oldFrequency),
            newFrequency = Frequency.valueOf(expenseHistory.newFrequency),
            action = Action.valueOf(expenseHistory.action),
            reason = expenseHistory.reason,
            entryDate = expenseHistory.entryDate
        )
    }
}