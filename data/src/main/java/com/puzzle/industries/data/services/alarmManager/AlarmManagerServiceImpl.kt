package com.puzzle.industries.data.services.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context

internal class AlarmManagerServiceImpl constructor(context: Context) : AlarmManagerService {

    private val alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    override fun setOnceOffAlarm(triggerAtMillis: Long, pendingIntent: PendingIntent) {
        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            pendingIntent
        )
    }

    override fun setRepetitiveAlarm(
        triggerAtMillis: Long,
        intervalMillis: Long,
        pendingIntent: PendingIntent
    ) {
        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis,
            pendingIntent
        )
    }

    override fun cancelAlarm(pendingIntent: PendingIntent) {
        alarmManager?.cancel(pendingIntent)
    }
}