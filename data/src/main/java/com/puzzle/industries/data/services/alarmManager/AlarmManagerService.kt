package com.puzzle.industries.data.services.alarmManager

import android.app.PendingIntent
import android.content.Intent

interface AlarmManagerService {
    fun setOnceOffAlarm(triggerAtMillis: Long, pendingIntent: PendingIntent)
    fun setRepetitiveAlarm(triggerAtMillis: Long, intervalMillis: Long, pendingIntent: PendingIntent)
    fun cancelAlarm(pendingIntent: PendingIntent)
    fun alarmIsSet(requestCode: Int, intent: Intent) : Boolean
}