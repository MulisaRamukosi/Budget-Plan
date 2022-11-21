package com.puzzle.industries.data.broadcastReceivers

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import com.puzzle.industries.domain.services.IncomeAlarmService
import dagger.hilt.android.AndroidEntryPoint
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import javax.inject.Inject

@AndroidEntryPoint
class SetAutoDeleteIncomeAlarmBroadcastReceiver : BroadcastReceiver() {

    @Inject
    lateinit var incomeAlarmService: IncomeAlarmService

    override fun onReceive(context: Context?, intent: Intent?) {
        CoroutineScope(Dispatchers.Default).launch {
            incomeAlarmService.setAutoDeleteIncomeAlarm()
        }
    }
}