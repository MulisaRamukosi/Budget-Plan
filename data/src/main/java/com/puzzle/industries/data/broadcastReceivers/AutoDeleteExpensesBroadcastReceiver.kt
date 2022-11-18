package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.puzzle.industries.data.R
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.data.storage.database.dao.expense.ExpenseDao
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.FrequencyDate
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.usescases.expense.ExpenseUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.filter
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import java.util.*
import javax.inject.Inject

@AndroidEntryPoint
internal class AutoDeleteExpensesBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var expenseUseCase: ExpenseUseCase
    @Inject
    lateinit var expenseDao: ExpenseDao
    @Inject
    lateinit var expenseMapper: ExpenseMapper
    @Inject
    lateinit var calendarService: CalendarService

    override fun onReceive(context: Context?, intent: Intent?) {
        runBlocking(context = Dispatchers.IO) {
            val currCalendar = calendarService.getInstance()

            val expenses =
                expenseDao.readAllExpensesByFrequencyType(frequencyType = FrequencyType.ONCE_OFF).first()
                    .map {
                        expenseMapper.toExpense(expense = it)
                    }
                    .filter {
                        val date = FrequencyDate.parseDate(date = it.frequencyWhen)
                        val expenseCalendar = calendarService.getInstance()
                        expenseCalendar.set(date.year, date.month, date.day)
                        currCalendar.after(expenseCalendar)
                    }
                    .toTypedArray()

            context?.let{
                expenseUseCase.delete.delete(
                    reason = context.getString(R.string.reason_auto_delete),
                    entity = expenses
                )
            }
        }
    }
}