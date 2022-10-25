package com.puzzle.industries.data.mapper.reminder

import com.puzzle.industries.data.storage.database.entity.reminder.ReminderEntity
import com.puzzle.industries.data.storage.database.entity.reminder.ReminderWithExpenseEntity
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.reminder.Reminder
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense

internal class ReminderMapper constructor(private val expenseMapper: ExpenseMapper) {

    fun toReminderWithExpense(reminderWithExpenseEntity: ReminderWithExpenseEntity) : ReminderWithExpense {
        return ReminderWithExpense(
            reminder = toReminder(reminderEntity = reminderWithExpenseEntity.reminder),
            expense = expenseMapper.toExpense(expense = reminderWithExpenseEntity.expense)
        )
    }

    fun toReminderWithExpenseEntity(reminderWithExpense: ReminderWithExpense) : ReminderWithExpenseEntity {
        return ReminderWithExpenseEntity(
            reminder = toReminderEntity(reminder = reminderWithExpense.reminder),
            expense = expenseMapper.toExpenseEntity(expense = reminderWithExpense.expense)
        )
    }

    fun toReminderEntity(expense: Expense): ReminderEntity {
        return ReminderEntity(
            id = 0,
            expenseId = expense.id,
            remind = true
        )
    }



    private fun toReminder(reminderEntity: ReminderEntity): Reminder {
        return Reminder(
            id = reminderEntity.id,
            expenseId = reminderEntity.expenseId,
            remind = reminderEntity.remind
        )
    }

    fun toReminderEntity(reminder: Reminder): ReminderEntity {
        return ReminderEntity(
            id = reminder.id,
            expenseId = reminder.expenseId,
            remind = reminder.remind
        )
    }

}