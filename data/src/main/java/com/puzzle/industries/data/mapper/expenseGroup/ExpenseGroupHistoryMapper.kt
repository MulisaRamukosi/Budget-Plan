package com.puzzle.industries.data.mapper.expenseGroup

import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupHistoryEntity
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupHistory

internal class ExpenseGroupHistoryMapper {

    fun toExpenseGroupHistoryEntity(expenseGroupHistory: ExpenseGroupHistory): ExpenseGroupHistoryEntity {
        return ExpenseGroupHistoryEntity(
            id = expenseGroupHistory.id,
            oldName = expenseGroupHistory.oldName,
            newName = expenseGroupHistory.newName,
            action = expenseGroupHistory.action.name,
            reason = expenseGroupHistory.reason
        )
    }

    fun toExpenseGroupEntity(expenseGroupHistory: ExpenseGroupHistoryEntity): ExpenseGroupHistory {
        return ExpenseGroupHistory(
            id = expenseGroupHistory.id,
            oldName = expenseGroupHistory.oldName,
            newName = expenseGroupHistory.newName,
            action = Action.valueOf(expenseGroupHistory.action),
            reason = expenseGroupHistory.reason,
            entryDate = expenseGroupHistory.entryDate
        )
    }

}