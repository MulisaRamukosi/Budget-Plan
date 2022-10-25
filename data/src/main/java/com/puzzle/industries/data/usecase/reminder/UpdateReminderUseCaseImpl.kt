package com.puzzle.industries.data.usecase.reminder

import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.usescases.reminder.UpdateReminderUseCase

internal class UpdateReminderUseCaseImpl constructor(
    private val reminderRepository: ReminderRepository
) : UpdateReminderUseCase {

    override suspend fun update(vararg reminders: ReminderWithExpense): Response<Boolean> =
        reminderRepository.update(entity = reminders)

}