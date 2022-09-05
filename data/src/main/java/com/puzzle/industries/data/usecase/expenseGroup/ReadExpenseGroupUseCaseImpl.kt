package com.puzzle.industries.data.usecase.expenseGroup

import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import com.puzzle.industries.domain.usescases.expenseGroup.ReadExpenseGroupUseCase
import kotlinx.coroutines.flow.Flow

class ReadExpenseGroupUseCaseImpl constructor(private val expenseGroupRepository: ExpenseGroupRepository) :
    ReadExpenseGroupUseCase {
    override fun read(): Response<Flow<List<ExpenseGroupWithExpenses>>> =
        expenseGroupRepository.read()
}