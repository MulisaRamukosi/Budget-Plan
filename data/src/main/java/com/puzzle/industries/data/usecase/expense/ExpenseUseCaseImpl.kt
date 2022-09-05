package com.puzzle.industries.data.usecase.expense

import com.puzzle.industries.domain.repository.expense.ExpenseHistoryRepository
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import com.puzzle.industries.domain.usescases.expense.DeleteExpenseUseCase
import com.puzzle.industries.domain.usescases.expense.ExpenseUseCase
import com.puzzle.industries.domain.usescases.expense.InsertExpenseUseCase
import com.puzzle.industries.domain.usescases.expense.UpdateExpenseUseCase

internal class ExpenseUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository,
    private val expenseHistoryRepository: ExpenseHistoryRepository
) : ExpenseUseCase {

    override val create: InsertExpenseUseCase
        get() = InsertExpenseUseCaseImpl(
            expenseRepository = expenseRepository,
            expenseHistoryRepository = expenseHistoryRepository
        )

    override val update: UpdateExpenseUseCase
        get() = UpdateExpenseUseCaseImpl(
            expenseRepository = expenseRepository,
            expenseHistoryRepository = expenseHistoryRepository
        )

    override val delete: DeleteExpenseUseCase
        get() = DeleteExpenseUseCaseImpl(
            expenseRepository = expenseRepository,
            expenseHistoryRepository = expenseHistoryRepository
        )

}