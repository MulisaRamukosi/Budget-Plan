package com.puzzle.industries.domain.repository.expense

import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.common.crud.*
import com.puzzle.industries.domain.common.response.Response
import kotlinx.coroutines.flow.Flow

interface ExpenseRepository : Insert<Expense>, Update<Expense>, Delete<Expense> {
    fun getExpensesWithNoAlarms(): Response<Flow<List<Expense>>>
}