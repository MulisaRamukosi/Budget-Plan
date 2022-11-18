package com.puzzle.industries.data.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.puzzle.industries.data.broadcastReceivers.AutoDeleteExpensesBroadcastReceiver
import com.puzzle.industries.data.services.alarmManager.AlarmManagerService
import com.puzzle.industries.data.util.config.AutoDeleteAlarmConfig
import com.puzzle.industries.domain.datastores.ExpenseDataStore
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.services.ExpenseAlarmService
import java.util.*

internal class ExpenseAlarmServiceImpl constructor(
    private val context: Context,
    private val calendarService: CalendarService,
    private val alarmManagerService: AlarmManagerService,
    private val expenseDataStore: ExpenseDataStore
) : ExpenseAlarmService {

    private val requestCode = 8028

    private val autoDeleteExpenseWithReminderPendingIntent: PendingIntent =
        PendingIntent.getBroadcast(
            context,
            requestCode,
            Intent(context, AutoDeleteExpensesBroadcastReceiver::class.java),
            PendingIntent.FLAG_IMMUTABLE
        )

    override suspend fun setAutoDeleteExpenseAlarm() {
        expenseDataStore.getAutoDeleteExpenseState().collect { enabled ->
            val alarmIsSet = alarmManagerService.alarmIsSet(
                requestCode = requestCode,
                intent = Intent(context, AutoDeleteExpensesBroadcastReceiver::class.java)
            )
            if (enabled && !alarmIsSet) {
                setAlarm()
            } else {
                alarmManagerService.cancelAlarm(pendingIntent = autoDeleteExpenseWithReminderPendingIntent)
            }
        }
    }

    private fun setAlarm() {
        val calendar = calendarService.getInstance()

        calendar.add(Calendar.DAY_OF_MONTH, 1)
        calendar.set(Calendar.HOUR_OF_DAY, AutoDeleteAlarmConfig.HOUR_OF_DAY)
        calendar.set(Calendar.MINUTE, AutoDeleteAlarmConfig.MINUTE)

        alarmManagerService.setRepetitiveAlarm(
            triggerAtMillis = calendar.timeInMillis,
            intervalMillis = AlarmManager.INTERVAL_DAY,
            pendingIntent = autoDeleteExpenseWithReminderPendingIntent
        )
    }
}