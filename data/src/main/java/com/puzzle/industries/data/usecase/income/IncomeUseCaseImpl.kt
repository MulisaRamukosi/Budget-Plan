package com.puzzle.industries.data.usecase.income

import com.puzzle.industries.domain.repository.income.IncomeHistoryRepository
import com.puzzle.industries.domain.repository.income.IncomeRepository
import com.puzzle.industries.domain.usescases.income.*

internal class IncomeUseCaseImpl constructor(
    private val incomeRepository: IncomeRepository,
    private val incomeHistoryRepository: IncomeHistoryRepository
) : IncomeUseCase {

    override val create: InsertIncomeUseCase
        get() = InsertIncomeUseCaseImpl(
            incomeRepository = incomeRepository,
            incomeHistoryRepository = incomeHistoryRepository
        )

    override val read: ReadIncomeUseCase
        get() = ReadIncomeUseCaseImpl(incomeRepository = incomeRepository)

    override val update: UpdateIncomeUseCase
        get() = UpdateIncomeUseCaseImpl(
            incomeRepository = incomeRepository,
            incomeHistoryRepository = incomeHistoryRepository
        )

    override val delete: DeleteIncomeUseCase
        get() = DeleteIncomeUseCaseImpl(
            incomeRepository = incomeRepository,
            incomeHistoryRepository = incomeHistoryRepository
        )

}