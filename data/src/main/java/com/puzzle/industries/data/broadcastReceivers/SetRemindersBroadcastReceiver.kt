package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.data.mapper.reminder.ReminderMapper
import com.puzzle.industries.data.storage.database.dao.reminder.ReminderDao
import com.puzzle.industries.data.storage.database.entity.reminder.ReminderWithExpenseEntity
import com.puzzle.industries.domain.services.ReminderService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import javax.inject.Inject

@AndroidEntryPoint
internal class SetRemindersBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var reminderDao: ReminderDao
    @Inject
    lateinit var reminderService: ReminderService
    @Inject
    lateinit var reminderMapper: ReminderMapper
    @Inject
    lateinit var expenseMapper: ExpenseMapper

    override fun onReceive(context: Context?, intent: Intent?) {
        runBlocking(context = Dispatchers.IO) {
            val expensesWithReminders = reminderDao.read().first()
                .map { reminderMapper.toReminderWithExpense(reminderWithExpenseEntity = it) }
                .toTypedArray()
            reminderService.setReminder(reminderWithExpenses = expensesWithReminders)
        }
    }
}