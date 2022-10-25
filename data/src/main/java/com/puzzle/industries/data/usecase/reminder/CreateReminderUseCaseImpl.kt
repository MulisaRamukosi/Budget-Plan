package com.puzzle.industries.data.usecase.reminder

import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.reminder.Reminder
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.usescases.reminder.CreateReminderUseCase

internal class CreateReminderUseCaseImpl constructor(
    private val reminderRepository: ReminderRepository
) : CreateReminderUseCase {

    override suspend fun create(vararg expenses: Expense): Response<Boolean> =
        reminderRepository.insert(entity = expenses.map {
            ReminderWithExpense(
                reminder = Reminder(
                    id = 0,
                    expenseId = it.id,
                    remind = true
                ),
                expense = it
            )
        }.toTypedArray())
}