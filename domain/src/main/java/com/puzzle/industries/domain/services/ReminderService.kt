package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.models.reminder.ReminderWithExpense

interface ReminderService {
    fun setReminder(vararg reminderWithExpenses: ReminderWithExpense)
    fun cancelReminder(vararg reminderIds: Int)
}