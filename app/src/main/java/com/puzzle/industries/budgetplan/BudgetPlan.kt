package com.puzzle.industries.budgetplan

import android.app.Application
import com.puzzle.industries.data.notification.NotificationChannelService
import com.puzzle.industries.domain.services.ExpenseAlarmService
import com.puzzle.industries.domain.services.IncomeAlarmService
import dagger.hilt.android.HiltAndroidApp
import kotlinx.coroutines.*
import javax.inject.Inject

@HiltAndroidApp
class BudgetPlan : Application() {

    @Inject lateinit var notificationChannelService: NotificationChannelService
    @Inject lateinit var expenseAlarmService: ExpenseAlarmService
    @Inject lateinit var incomeAlarmService: IncomeAlarmService

    override fun onCreate() {
        super.onCreate()
        notificationChannelService.createNotificationChannels()
        setAutoDeleteAlarms()
    }

    private fun setAutoDeleteAlarms(){
        CoroutineScope(context = Dispatchers.IO).launch {
            expenseAlarmService.setAutoDeleteExpenseAlarm()
            incomeAlarmService.setAutoDeleteExpenseAlarm()
        }
    }
}