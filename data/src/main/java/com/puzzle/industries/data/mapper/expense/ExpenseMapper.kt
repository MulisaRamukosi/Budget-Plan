package com.puzzle.industries.data.mapper.expense

import com.puzzle.industries.data.database.entity.expense.ExpenseEntity
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.expense.Expense
import kotlin.math.exp

internal class ExpenseMapper {

    fun toExpenseEntity(expense: Expense) : ExpenseEntity{
        return ExpenseEntity(
            id = expense.id,
            expenseGroupId = expense.expenseGroupId,
            name = expense.name,
            amount = expense.amount,
            frequency = expense.frequency.name,
        )
    }

    fun toExpense(expense: ExpenseEntity) : Expense{
        return Expense(
            id = expense.id,
            expenseGroupId = expense.expenseGroupId,
            name = expense.name,
            amount = expense.amount,
            frequency = Frequency.valueOf(expense.frequency),
        )
    }
}