package com.puzzle.industries.data.usecase.reminder

import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.usescases.reminder.*

internal class ReminderUseCaseImpl constructor(
    private val reminderRepository: ReminderRepository,
    private val expenseRepository: ExpenseRepository
) :
    ReminderUseCase {

    override val read: ReadReminderUseCase
        get() = ReadReminderUseCaseImpl(reminderRepository = reminderRepository)

    override val create: CreateReminderUseCase
        get() = CreateReminderUseCaseImpl(reminderRepository = reminderRepository)

    override val delete: DeleteReminderUseCase
        get() = DeleteReminderUseCaseImpl(reminderRepository = reminderRepository)

    override val update: UpdateReminderUseCase
        get() = UpdateReminderUseCaseImpl(reminderRepository = reminderRepository)

    override val readExpensesWithNoReminders: ReadExpensesWithNoRemindersUseCase
        get() = ReadExpensesWithNoRemindersUseCaseImpl(expenseRepository = expenseRepository)
}