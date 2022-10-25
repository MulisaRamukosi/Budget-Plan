package com.puzzle.industries.data.usecase.reminder

import com.puzzle.industries.data.storage.database.dao.reminder.ReminderDao
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.reminder.Reminder
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.usescases.reminder.ReadReminderUseCase
import kotlinx.coroutines.flow.Flow

internal class ReadReminderUseCaseImpl constructor(private val reminderRepository: ReminderRepository)
    : ReadReminderUseCase {
    override fun readAll(): Response<Flow<List<ReminderWithExpense>>> = reminderRepository.read()
}