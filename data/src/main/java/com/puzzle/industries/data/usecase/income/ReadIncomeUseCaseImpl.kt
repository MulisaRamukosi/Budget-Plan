package com.puzzle.industries.data.usecase.income

import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.repository.income.IncomeRepository
import com.puzzle.industries.domain.usescases.income.ReadIncomeUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

internal class ReadIncomeUseCaseImpl constructor(
    private val incomeRepository: IncomeRepository
) : ReadIncomeUseCase {
    override fun read(): Response<Flow<List<Income>>> = incomeRepository.read()
}