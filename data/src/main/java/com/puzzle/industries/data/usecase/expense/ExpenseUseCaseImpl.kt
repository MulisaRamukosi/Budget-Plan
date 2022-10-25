package com.puzzle.industries.data.usecase.expense

import com.puzzle.industries.domain.repository.expense.ExpenseHistoryRepository
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.usescases.expense.DeleteExpenseUseCase
import com.puzzle.industries.domain.usescases.expense.ExpenseUseCase
import com.puzzle.industries.domain.usescases.expense.InsertExpenseUseCase
import com.puzzle.industries.domain.usescases.expense.UpdateExpenseUseCase

internal class ExpenseUseCaseImpl constructor(
    private val expenseRepository: ExpenseRepository,
    private val expenseHistoryRepository: ExpenseHistoryRepository,
    private val reminderRepository: ReminderRepository
) : ExpenseUseCase {

    override val create: InsertExpenseUseCase
        get() = InsertExpenseUseCaseImpl(
            expenseRepository = expenseRepository,
            expenseHistoryRepository = expenseHistoryRepository
        )

    override val update: UpdateExpenseUseCase
        get() = UpdateExpenseUseCaseImpl(
            expenseRepository = expenseRepository,
            expenseHistoryRepository = expenseHistoryRepository,
            reminderRepository = reminderRepository
        )

    override val delete: DeleteExpenseUseCase
        get() = DeleteExpenseUseCaseImpl(
            expenseRepository = expenseRepository,
            expenseHistoryRepository = expenseHistoryRepository,
            reminderRepository = reminderRepository
        )

}