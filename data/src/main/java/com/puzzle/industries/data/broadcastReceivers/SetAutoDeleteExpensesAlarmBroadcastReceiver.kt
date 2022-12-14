package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.puzzle.industries.domain.services.ExpenseAlarmService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.*
import javax.inject.Inject

@AndroidEntryPoint
internal class SetAutoDeleteExpensesAlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var expenseAlarmService: ExpenseAlarmService

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.Default).launch {
            expenseAlarmService.setAutoDeleteExpenseAlarm()
        }
    }
}