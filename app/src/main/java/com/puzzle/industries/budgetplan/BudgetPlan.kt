package com.puzzle.industries.budgetplan

import android.app.Application
import com.puzzle.industries.data.notification.NotificationChannelService
import com.puzzle.industries.domain.services.AutoDeleteExpensesAlarmService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltAndroidApp
class BudgetPlan : Application() {

    @Inject lateinit var notificationChannelService: NotificationChannelService
    @Inject lateinit var autoDeleteExpensesAlarmService: AutoDeleteExpensesAlarmService

    override fun onCreate() {
        super.onCreate()
        notificationChannelService.createNotificationChannels()
        CoroutineScope(Dispatchers.IO).launch {
            autoDeleteExpensesAlarmService.setAutoDeleteExpenseAlarm()
        }
    }
}