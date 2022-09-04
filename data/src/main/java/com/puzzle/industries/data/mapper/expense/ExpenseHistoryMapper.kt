package com.puzzle.industries.data.mapper.expense

import com.puzzle.industries.data.database.entity.expense.ExpenseHistoryEntity
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.expense.ExpenseHistory
import java.util.*
import javax.inject.Inject

internal class ExpenseHistoryMapper @Inject constructor(private val expenseMapper: ExpenseMapper){

    fun toExpenseHistoryEntity(expenseHistory: ExpenseHistory): ExpenseHistoryEntity {
        return ExpenseHistoryEntity(
            id = expenseHistory.id,
            expense = expenseMapper.toExpenseEntity(expenseHistory.expense),
            action = expenseHistory.action,
            reason = expenseHistory.reason
        )
    }

    fun toExpenseHistory(expenseHistory: ExpenseHistoryEntity): ExpenseHistory {
        return ExpenseHistory(
            id = expenseHistory.id,
            expense = expenseMapper.toExpense(expenseHistory.expense),
            action = expenseHistory.action,
            reason = expenseHistory.reason,
            entryDate = expenseHistory.entryDate
        )
    }
}