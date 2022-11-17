package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.puzzle.industries.domain.services.AutoDeleteExpensesAlarmService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject

@AndroidEntryPoint
internal class SetAutoDeleteExpensesBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var autoDeleteExpensesAlarmService: AutoDeleteExpensesAlarmService

    override fun onReceive(context: Context?, intent: Intent?) {
        runBlocking {
            withContext(context = Dispatchers.IO){
                autoDeleteExpensesAlarmService.setAutoDeleteExpenseAlarm()
            }
        }
    }
}