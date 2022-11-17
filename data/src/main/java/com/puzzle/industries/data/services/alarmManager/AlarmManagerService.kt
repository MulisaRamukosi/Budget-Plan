package com.puzzle.industries.data.services.alarmManager

import android.app.PendingIntent

interface AlarmManagerService {
    fun setOnceOffAlarm(triggerAtMillis: Long, pendingIntent: PendingIntent)
    fun setRepetitiveAlarm(triggerAtMillis: Long, intervalMillis: Long, pendingIntent: PendingIntent)
    fun cancelAlarm(pendingIntent: PendingIntent)
}