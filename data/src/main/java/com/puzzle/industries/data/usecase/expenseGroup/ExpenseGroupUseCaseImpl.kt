package com.puzzle.industries.data.usecase.expenseGroup

import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupHistoryRepository
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import com.puzzle.industries.domain.usescases.expenseGroup.*

class ExpenseGroupUseCaseImpl constructor(
    private val expenseGroupRepository: ExpenseGroupRepository,
    private val expenseGroupHistoryRepository: ExpenseGroupHistoryRepository
) : ExpenseGroupUseCase {

    override val create: InsertExpenseGroupUseCase
        get() = InsertExpenseGroupUseCaseImpl(
            expenseGroupRepository = expenseGroupRepository,
            expenseGroupHistoryRepository = expenseGroupHistoryRepository
        )

    override val update: UpdateExpenseGroupUseCase
        get() = UpdateExpenseGroupUseCaseImpl(
            expenseGroupRepository = expenseGroupRepository,
            expenseGroupHistoryRepository = expenseGroupHistoryRepository
        )

    override val delete: DeleteExpenseGroupUseCase
        get() = DeleteExpenseGroupUseCaseImpl(
            expenseGroupRepository = expenseGroupRepository,
            expenseGroupHistoryRepository = expenseGroupHistoryRepository
        )

    override val read: ReadExpenseGroupUseCase
        get() = ReadExpenseGroupUseCaseImpl(expenseGroupRepository = expenseGroupRepository)
}