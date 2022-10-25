package com.puzzle.industries.data.services

import android.app.AlarmManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import com.puzzle.industries.data.broadcastReceivers.ReminderBroadcastReceiver
import com.puzzle.industries.data.util.config.ReminderConfig
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.FrequencyDate
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense
import com.puzzle.industries.domain.services.ReminderService
import java.util.*

internal class ReminderServiceImpl(private val context: Context) : ReminderService {

    private var alarmManager: AlarmManager? =
        context.getSystemService(Context.ALARM_SERVICE) as? AlarmManager

    override fun setReminder(vararg reminderWithExpenses: ReminderWithExpense) {
        reminderWithExpenses.forEach { reminderWithExpense ->
            val expense = reminderWithExpense.expense
            val reminderId = reminderWithExpense.reminder.id

            when (expense.frequencyType) {
                FrequencyType.ONCE_OFF -> setOnceOffReminder(
                    reminderId = reminderId,
                    expense = expense
                )
                FrequencyType.DAILY -> setDailyReminder(reminderId = reminderId, expense = expense)
                FrequencyType.WEEKLY -> setWeeklyReminder(
                    reminderId = reminderId,
                    expense = expense
                )
                else -> {}
            }
        }

    }

    override fun cancelReminder(vararg reminderIds: Int) {
        reminderIds.forEach { reminderId ->
            alarmManager?.cancel(buildPendingIntent(requestId = reminderId))
        }
    }

    private fun getWeekDay(day: String): Int {
        return WeekDays.valueOf(day).ordinal + 1
    }

    private fun setOnceOffReminder(reminderId: Int, expense: Expense) {
        val calendar: Calendar = Calendar.getInstance()
        val date = FrequencyDate.parseDate(date = expense.frequencyWhen)

        calendar.set(Calendar.DAY_OF_MONTH, date.day)
        calendar.set(Calendar.MONTH, date.month)
        calendar.set(Calendar.YEAR, date.year)

        setOnceOffAlarm(
            requestId = reminderId,
            expense = expense,
            triggerAtMillis = calendar.timeInMillis
        )
    }

    private fun setWeeklyReminder(reminderId: Int, expense: Expense) {
        val calendar: Calendar = Calendar.getInstance()

        val alarmDay = getWeekDay(day = expense.frequencyWhen)
        val currentDay = calendar.get(Calendar.DAY_OF_WEEK)

        if (currentDay > alarmDay) {
            calendar.add(Calendar.WEEK_OF_YEAR, 1)
        }

        calendar.set(Calendar.HOUR_OF_DAY, ReminderConfig.HOUR_OF_DAY)
        calendar.set(Calendar.MINUTE, ReminderConfig.MINUTE)
        calendar.set(Calendar.DAY_OF_WEEK, alarmDay)

        setRepeatingAlarm(
            requestId = reminderId,
            expense = expense,
            triggerAtMillis = calendar.timeInMillis,
            intervalMillis = AlarmManager.INTERVAL_DAY * 7
        )
    }

    private fun setDailyReminder(reminderId: Int, expense: Expense) {
        val calendar: Calendar = Calendar.getInstance()

        if (calendar.get(Calendar.HOUR_OF_DAY) >= ReminderConfig.HOUR_OF_DAY
            && calendar.get(Calendar.MINUTE) > ReminderConfig.MINUTE
        ) {
            calendar.add(Calendar.DAY_OF_MONTH, 1)
        }

        calendar.set(Calendar.HOUR_OF_DAY, ReminderConfig.HOUR_OF_DAY)
        calendar.set(Calendar.MINUTE, ReminderConfig.MINUTE)

        setRepeatingAlarm(
            requestId = reminderId,
            expense = expense,
            triggerAtMillis = calendar.timeInMillis,
            intervalMillis = AlarmManager.INTERVAL_DAY
        )
    }

    private fun setOnceOffAlarm(requestId: Int, expense: Expense, triggerAtMillis: Long) {
        alarmManager?.set(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            buildPendingIntent(requestId = requestId, expense = expense)
        )
    }

    private fun setRepeatingAlarm(
        requestId: Int,
        expense: Expense,
        triggerAtMillis: Long,
        intervalMillis: Long
    ) {
        alarmManager?.setInexactRepeating(
            AlarmManager.RTC_WAKEUP,
            triggerAtMillis,
            intervalMillis,
            buildPendingIntent(requestId = requestId, expense = expense)
        )
    }

    private fun buildPendingIntent(requestId: Int, expense: Expense? = null): PendingIntent {
        return Intent(context, ReminderBroadcastReceiver::class.java).let { intent ->
            intent.putExtra(ReminderConfig.KEY_ID, requestId)
            expense?.let {
                intent.putExtra(ReminderConfig.KEY_TITLE, expense.name)
                intent.putExtra(ReminderConfig.KEY_AMOUNT, expense.amount)
            }
            PendingIntent.getBroadcast(context, requestId, intent, 0)
        }
    }
}