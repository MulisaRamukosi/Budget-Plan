package com.puzzle.industries.data.mapper.expenseGroup

import com.puzzle.industries.data.storage.database.entity.expenseGroup.ExpenseGroupHistoryEntity
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupHistory
import javax.inject.Inject

internal class ExpenseGroupHistoryMapper @Inject constructor(private val expenseGroupMapper: ExpenseGroupMapper){

    fun toExpenseGroupHistoryEntity(expenseGroupHistory: ExpenseGroupHistory): ExpenseGroupHistoryEntity {
        return ExpenseGroupHistoryEntity(
            id = expenseGroupHistory.id,
            expenseGroup = expenseGroupMapper.toExpenseGroupEntity(expenseGroupHistory.expenseGroup),
            action = expenseGroupHistory.action,
            reason = expenseGroupHistory.reason
        )
    }

    fun toExpenseGroupHistory(expenseGroupHistory: ExpenseGroupHistoryEntity): ExpenseGroupHistory {
        return ExpenseGroupHistory(
            id = expenseGroupHistory.id,
            expenseGroup = expenseGroupMapper.toExpenseGroup(expenseGroupHistory.expenseGroup),
            action = expenseGroupHistory.action,
            reason = expenseGroupHistory.reason,
            entryDate = expenseGroupHistory.entryDate
        )
    }

}