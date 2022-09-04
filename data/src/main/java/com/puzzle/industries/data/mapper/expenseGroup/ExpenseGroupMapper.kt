package com.puzzle.industries.data.mapper.expenseGroup

import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupEntity
import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupWithExpensesEntity
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses

internal class ExpenseGroupMapper {

    fun toExpenseGroupEntity(expenseGroup: ExpenseGroup): ExpenseGroupEntity {
        return ExpenseGroupEntity(
            id = expenseGroup.id,
            name = expenseGroup.name
        )
    }

    fun toExpenseGroupWithExpenses(entity: ExpenseGroupWithExpensesEntity): ExpenseGroupWithExpenses {
        return ExpenseGroupWithExpenses(
            expenseGroup = toExpenseGroup(entity.expenseGroup),
            expenses = entity.expenses.map { expenseEntity ->
                Expense(
                    id = expenseEntity.id,
                    expenseGroupId = expenseEntity.expenseGroupId,
                    name = expenseEntity.name,
                    amount = expenseEntity.amount,
                    frequency = expenseEntity.frequency
                )
            }
        )
    }

    private fun toExpenseGroup(expenseGroup: ExpenseGroupEntity): ExpenseGroup {
        return ExpenseGroup(
            id = expenseGroup.id,
            name = expenseGroup.name,
            lastModifyDate = expenseGroup.lastModifyDate
        )
    }
}