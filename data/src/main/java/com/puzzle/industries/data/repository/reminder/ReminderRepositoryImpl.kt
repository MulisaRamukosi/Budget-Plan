package com.puzzle.industries.data.repository.reminder

import com.puzzle.industries.data.mapper.reminder.ReminderMapper
import com.puzzle.industries.data.storage.database.dao.reminder.ReminderDao
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.reminder.Reminder
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.services.ReminderService
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.runBlocking

internal class ReminderRepositoryImpl constructor(
    private val reminderMapper: ReminderMapper,
    private val reminderDao: ReminderDao,
    private val reminderService: ReminderService
) : ReminderRepository {

    override suspend fun cancelRemindersForExpenses(vararg expenses: Expense): Response<Boolean> {
        val expenseIds = expenses.map { it.id }.toTypedArray()
        val reminderIds = reminderDao.getReminderIdByExpenseId(expenseIds = expenseIds).first()

        runBlocking(Dispatchers.IO) { reminderDao.deleteRemindersByExpenseIds(expenseIds = expenseIds) }
        reminderService.cancelReminder(reminderIds = reminderIds.toIntArray())

        return Response(response = true)
    }

    override suspend fun updateReminderForExpenses(vararg expense: Expense): Response<Boolean> {
        runBlocking(context = Dispatchers.IO) {
            val reminders =
                reminderDao.getEnabledRemindersByExpenseIds(expenseIds = expense.map { it.id }
                    .toTypedArray())

            reminders.forEach { reminderWithExpenseEntity ->
                reminderService.cancelReminder(reminderWithExpenseEntity.reminder.id)

                reminderService.setReminder(
                    reminderMapper.toReminderWithExpense(reminderWithExpenseEntity = reminderWithExpenseEntity)
                )
            }
        }

        return Response(true)
    }

    override suspend fun insert(vararg entity: ReminderWithExpense): Response<Boolean> {
        val reminderEntities = entity.map { reminderMapper.toReminderEntity(reminder = it.reminder) }.toTypedArray()
        val response = reminderDao.insert(entity = reminderEntities)

        response.forEachIndexed { idx, reminderId ->
            entity[idx].reminder.id = reminderId.toInt()
        }

        reminderService.setReminder(reminderWithExpenses = entity)

        return Response(response = response.size == entity.size)
    }

    override fun read(): Response<Flow<List<ReminderWithExpense>>> {
        return Response(
            response = reminderDao.read().flowOn(context = Dispatchers.IO).map {
                val entities = mutableListOf<ReminderWithExpense>()
                it.forEach { reminderWithExpenseEntity ->
                    entities.add(reminderMapper.toReminderWithExpense(reminderWithExpenseEntity = reminderWithExpenseEntity))
                }
                return@map entities
            }
        )
    }

    override suspend fun delete(vararg entity: Reminder): Response<Boolean> {
        val response = reminderDao.delete(
            entity = entity.map {
                reminderMapper.toReminderEntity(reminder = it)
            }.toTypedArray()
        )

        reminderService.cancelReminder(reminderIds = entity.map { it.id }.toIntArray())

        return Response(response = response > 0)
    }

    override suspend fun update(vararg entity: ReminderWithExpense): Response<Boolean> {
        val response = reminderDao.update(entity = entity.map {
            reminderMapper.toReminderEntity(reminder = it.reminder)
        }.toTypedArray())

        entity.forEach { reminderWithExpense ->
            if (reminderWithExpense.reminder.remind) {
                reminderService.setReminder(reminderWithExpense)
            } else {
                reminderService.cancelReminder(reminderWithExpense.reminder.id)
            }
        }

        return Response(response = response > 0)
    }


}