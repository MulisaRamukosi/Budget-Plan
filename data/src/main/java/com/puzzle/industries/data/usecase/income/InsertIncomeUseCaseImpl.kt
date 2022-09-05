package com.puzzle.industries.data.usecase.income

import com.puzzle.industries.data.util.historyHelpers.HistoryGeneratorDelegate
import com.puzzle.industries.data.util.historyHelpers.IncomeHistoryGenerator
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.models.income.IncomeHistory
import com.puzzle.industries.domain.repository.income.IncomeHistoryRepository
import com.puzzle.industries.domain.repository.income.IncomeRepository
import com.puzzle.industries.domain.usescases.income.InsertIncomeUseCase
import javax.inject.Inject

internal class InsertIncomeUseCaseImpl constructor(
    private val incomeRepository: IncomeRepository,
    private val incomeHistoryRepository: IncomeHistoryRepository
) : InsertIncomeUseCase,
    HistoryGeneratorDelegate<Income, IncomeHistory> by IncomeHistoryGenerator() {

    override suspend fun insert(reason: String, vararg entity: Income): Response<Boolean> {
        val result = incomeRepository.insert(entity = entity)

        if (result.response) {
            val incomeInsertHistory =
                generateHistory(reason = reason, action = Action.CREATE, entity = entity)

            incomeHistoryRepository.insert(entity = incomeInsertHistory)
        }

        return result
    }


}