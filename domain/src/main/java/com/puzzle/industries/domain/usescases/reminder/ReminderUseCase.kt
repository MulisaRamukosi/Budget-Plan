package com.puzzle.industries.domain.usescases.reminder

import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.reminder.Reminder
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense
import com.puzzle.industries.domain.usescases.base.UseCaseRead

interface CreateReminderUseCase {
    suspend fun create(vararg expenses: Expense): Response<Boolean>
}

interface DeleteReminderUseCase {
    suspend fun delete(vararg reminders: Reminder): Response<Boolean>
}

interface UpdateReminderUseCase {
    suspend fun update(vararg reminders: ReminderWithExpense): Response<Boolean>
}

interface ReadReminderUseCase : UseCaseRead<ReminderWithExpense>
interface ReadExpensesWithNoRemindersUseCase : UseCaseRead<Expense>


interface ReminderUseCase {
    val read: ReadReminderUseCase
    val create: CreateReminderUseCase
    val delete: DeleteReminderUseCase
    val update: UpdateReminderUseCase
    val readExpensesWithNoReminders: ReadExpensesWithNoRemindersUseCase
}