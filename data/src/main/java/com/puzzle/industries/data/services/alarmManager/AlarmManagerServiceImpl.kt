package com.puzzle.industries.data.services.alarmManager

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent

internal class AlarmManagerServiceImpl constructor(private val context: Context) : AlarmManagerService {

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

    override fun alarmIsSet(requestCode: Int, intent: Intent):Boolean {
        return PendingIntent.getBroadcast(
            context,
            requestCode,
            intent,
            PendingIntent.FLAG_NO_CREATE or PendingIntent.FLAG_IMMUTABLE
        ) != null
    }
}