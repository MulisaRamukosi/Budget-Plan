package com.puzzle.industries.data.usecase.expense

import com.puzzle.industries.data.util.historyHelpers.ExpenseHistoryGenerator
import com.puzzle.industries.data.util.historyHelpers.HistoryGeneratorDelegate
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.expense.ExpenseHistory
import com.puzzle.industries.domain.repository.expense.ExpenseHistoryRepository
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import com.puzzle.industries.domain.usescases.expense.UpdateExpenseUseCase

internal class UpdateExpenseUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository,
    private val expenseHistoryRepository: ExpenseHistoryRepository
) : UpdateExpenseUseCase,
    HistoryGeneratorDelegate<Expense, ExpenseHistory> by ExpenseHistoryGenerator() {

    override suspend fun update(reason: String, vararg entity: Expense): Response<Boolean> {
        val result = expenseRepository.insert(entity = entity)

        if(result.response){
            val expenseHistory =
                generateHistory(reason = reason, action = Action.UPDATE, entity = entity)
            expenseHistoryRepository.insert(entity = expenseHistory)
        }

        return result
    }
}