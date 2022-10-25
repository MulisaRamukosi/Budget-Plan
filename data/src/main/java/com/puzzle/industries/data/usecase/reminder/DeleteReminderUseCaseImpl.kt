package com.puzzle.industries.data.usecase.reminder

import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.reminder.Reminder
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.usescases.reminder.DeleteReminderUseCase

internal class DeleteReminderUseCaseImpl constructor(
    private val reminderRepository: ReminderRepository
) : DeleteReminderUseCase {
    override suspend fun delete(vararg reminders: Reminder): Response<Boolean> =
        reminderRepository.delete(entity = reminders)
}