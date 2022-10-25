package com.puzzle.industries.domain.repository.reminder

import com.puzzle.industries.domain.common.crud.Delete
import com.puzzle.industries.domain.common.crud.Insert
import com.puzzle.industries.domain.common.crud.Read
import com.puzzle.industries.domain.common.crud.Update
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.reminder.Reminder
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense

interface ReminderRepository : Insert<ReminderWithExpense>, Read<ReminderWithExpense>, Delete<Reminder>,
    Update<ReminderWithExpense> {
    suspend fun cancelRemindersForExpenses(vararg expenses: Expense): Response<Boolean>
    suspend fun updateReminderForExpenses(vararg expense: Expense): Response<Boolean>
}