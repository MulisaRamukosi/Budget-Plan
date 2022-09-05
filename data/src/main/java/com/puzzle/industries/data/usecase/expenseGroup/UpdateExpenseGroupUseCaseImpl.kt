package com.puzzle.industries.data.usecase.expenseGroup

import com.puzzle.industries.data.util.historyHelpers.ExpenseGroupHistoryGenerator
import com.puzzle.industries.data.util.historyHelpers.HistoryGeneratorDelegate
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupHistory
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupHistoryRepository
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import com.puzzle.industries.domain.usescases.expenseGroup.UpdateExpenseGroupUseCase

class UpdateExpenseGroupUseCaseImpl constructor(
    private val expenseGroupRepository: ExpenseGroupRepository,
    private val expenseGroupHistoryRepository: ExpenseGroupHistoryRepository
) : UpdateExpenseGroupUseCase,
    HistoryGeneratorDelegate<ExpenseGroup, ExpenseGroupHistory> by ExpenseGroupHistoryGenerator() {

    override suspend fun update(reason: String, vararg entity: ExpenseGroup): Response<Boolean> {
        val result = expenseGroupRepository.update(entity = entity)

        if (result.response) {
            val expenseGroupHistory =
                generateHistory(reason = reason, action = Action.UPDATE, entity = entity)
            expenseGroupHistoryRepository.insert(entity = expenseGroupHistory)
        }

        return result
    }

}