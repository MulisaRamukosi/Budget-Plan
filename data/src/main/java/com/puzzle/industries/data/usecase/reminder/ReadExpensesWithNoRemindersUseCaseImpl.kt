package com.puzzle.industries.data.usecase.reminder

import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import com.puzzle.industries.domain.usescases.reminder.ReadExpensesWithNoRemindersUseCase
import kotlinx.coroutines.flow.Flow

class ReadExpensesWithNoRemindersUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository
) : ReadExpensesWithNoRemindersUseCase {

    override fun readAll(): Response<Flow<List<Expense>>> =
        expenseRepository.getExpensesWithNoAlarms()
}