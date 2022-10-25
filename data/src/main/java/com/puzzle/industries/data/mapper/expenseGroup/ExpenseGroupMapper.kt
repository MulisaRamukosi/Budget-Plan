package com.puzzle.industries.data.mapper.expenseGroup

import com.puzzle.industries.data.storage.database.entity.expenseGroup.ExpenseGroupEntity
import com.puzzle.industries.data.storage.database.entity.expenseGroup.ExpenseGroupWithExpensesEntity
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses

internal class ExpenseGroupMapper constructor(private val expenseMapper: ExpenseMapper) {

    fun toExpenseGroupEntity(expenseGroup: ExpenseGroup): ExpenseGroupEntity {
        return ExpenseGroupEntity(
            id = expenseGroup.id,
            name = expenseGroup.name,
            description = expenseGroup.description,
            colorId = expenseGroup.colorId
        )
    }

    fun toExpenseGroupWithExpenses(entity: ExpenseGroupWithExpensesEntity): ExpenseGroupWithExpenses {
        return ExpenseGroupWithExpenses(
            expenseGroup = toExpenseGroup(entity.expenseGroup),
            expenses = entity.expenses.map { expenseEntity ->
                expenseMapper.toExpense(expense = expenseEntity)
            }
        )
    }

    fun toExpenseGroup(expenseGroup: ExpenseGroupEntity): ExpenseGroup {
        return ExpenseGroup(
            id = expenseGroup.id,
            name = expenseGroup.name,
            description = expenseGroup.description,
            colorId = expenseGroup.colorId,
            lastModifyDate = expenseGroup.lastModifyDate
        )
    }
}