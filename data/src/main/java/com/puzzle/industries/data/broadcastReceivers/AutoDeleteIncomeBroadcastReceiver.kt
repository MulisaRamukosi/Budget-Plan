package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.puzzle.industries.data.R
import com.puzzle.industries.data.mapper.income.IncomeMapper
import com.puzzle.industries.data.storage.database.dao.income.IncomeDao
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.FrequencyDate
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.usescases.income.IncomeUseCase
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
internal class AutoDeleteIncomeBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var incomeUseCase: IncomeUseCase
    @Inject
    lateinit var incomeDao: IncomeDao
    @Inject
    lateinit var incomeMapper: IncomeMapper
    @Inject
    lateinit var calendarService: CalendarService

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(context = Dispatchers.IO).launch {
            val currCalendar = calendarService.getInstance()

            val incomes =
                incomeDao.readAllIncomesByFrequencyType(frequencyType = FrequencyType.ONCE_OFF)
                    .first()
                    .map { incomeMapper.toIncome(income = it) }
                    .filter {
                        val date = FrequencyDate.parseDate(date = it.frequencyWhen)
                        val incomeCalendar = calendarService.getInstance()
                        incomeCalendar.set(date.year, date.month, date.day)
                        currCalendar.after(incomeCalendar)
                    }
                    .toTypedArray()

            context?.let {
                incomeUseCase.delete.delete(
                    reason = context.getString(R.string.reason_auto_delete),
                    entity = incomes
                )
            }
        }
    }
}