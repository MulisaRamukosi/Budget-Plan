package com.puzzle.industries.data.mapper.expense

import com.puzzle.industries.data.storage.database.entity.expense.ExpenseEntity
import com.puzzle.industries.domain.models.expense.Expense


internal class ExpenseMapper {

    fun toExpenseEntity(expense: Expense) : ExpenseEntity{
        return ExpenseEntity(
            id = expense.id,
            expenseGroupId = expense.expenseGroupId,
            name = expense.name,
            amount = expense.amount,
            frequencyType = expense.frequencyType,
            frequencyWhen = expense.frequencyWhen,
            description = expense.description
        )
    }

    fun toExpense(expense: ExpenseEntity) : Expense{
        return Expense(
            id = expense.id,
            expenseGroupId = expense.expenseGroupId,
            name = expense.name,
            amount = expense.amount,
            frequencyType = expense.frequencyType,
            frequencyWhen = expense.frequencyWhen,
            description = expense.description
        )
    }
}