package com.puzzle.industries.data.usecase.income

import com.puzzle.industries.data.util.historyHelpers.HistoryGeneratorDelegate
import com.puzzle.industries.data.util.historyHelpers.IncomeHistoryGenerator
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.models.income.IncomeHistory
import com.puzzle.industries.domain.repository.income.IncomeHistoryRepository
import com.puzzle.industries.domain.repository.income.IncomeRepository
import com.puzzle.industries.domain.usescases.income.UpdateIncomeUseCase
import javax.inject.Inject

internal class UpdateIncomeUseCaseImpl constructor(
    private val incomeRepository: IncomeRepository,
    private val incomeHistoryRepository: IncomeHistoryRepository
) : UpdateIncomeUseCase,
    HistoryGeneratorDelegate<Income, IncomeHistory> by IncomeHistoryGenerator() {

    override suspend fun update(reason: String, vararg entity: Income): Response<Boolean> {
        val result = incomeRepository.update(entity = entity)

        if (result.response) {
            val incomeInsertHistory =
                generateHistory(reason = reason, action = Action.UPDATE, entity = entity)

            incomeHistoryRepository.insert(entity = incomeInsertHistory)
        }

        return result
    }
}