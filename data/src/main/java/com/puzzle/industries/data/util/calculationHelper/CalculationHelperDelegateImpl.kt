package com.puzzle.industries.data.util.calculationHelper

import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.FrequencyDate
import java.util.*

class CalculationHelperDelegateImpl : CalculationHelperDelegate {

    override fun handleSumCalculation(
        amount: Double,
        frequencyType: FrequencyType,
        frequencyWhen: String,
        calendarInstance: Calendar
    ): Double {
        val includeInCurrentMonth = includeInCurrentMonth(
            frequencyType = frequencyType,
            frequencyWhen = frequencyWhen,
            calendar = calendarInstance
        )

        return if (includeInCurrentMonth) {
            calculateTotal(
                amount = amount,
                frequencyType = frequencyType,
                frequencyWhen = frequencyWhen,
                calendar = calendarInstance
            )
        } else 0.0
    }

    override fun calculateAmountForSingleMonth(
        amount: Double,
        frequencyType: FrequencyType,
        frequencyWhen: String,
        calendarInstance: Calendar
    ): Double = calculateTotal(
        amount = amount,
        frequencyType = frequencyType,
        frequencyWhen = frequencyWhen,
        calendar = calendarInstance
    )

    private fun includeInCurrentMonth(
        frequencyType: FrequencyType,
        frequencyWhen: String,
        calendar: Calendar
    ): Boolean {
        return when (frequencyType) {
            FrequencyType.ONCE_OFF -> {
                val date = FrequencyDate.parseDate(date = frequencyWhen)
                calendar.get(Calendar.MONTH) == date.month && calendar.get(Calendar.YEAR) == date.year
            }
            FrequencyType.YEARLY -> {
                val date = FrequencyDate.parseDayMonth(date = frequencyWhen)
                calendar.get(Calendar.MONTH) == date.month
            }
            else -> true
        }
    }

    private fun calculateTotal(
        amount: Double,
        frequencyType: FrequencyType,
        frequencyWhen: String,
        calendar: Calendar
    ): Double {

        return when (frequencyType) {
            FrequencyType.DAILY -> {
                val totalDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
                totalDaysInMonth * amount
            }
            FrequencyType.WEEKLY -> {
                val weekDay = WeekDays.valueOf(value = frequencyWhen).ordinal + 1
                val totalWeekDays =
                    calculateTotalWeekDayInMonth(calendar = calendar, weekDay = weekDay)
                totalWeekDays * amount
            }
            else -> amount
        }
    }

    private fun calculateTotalWeekDayInMonth(calendar: Calendar, weekDay: Int): Int {
        var count = 0
        val totalDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (day in 1..totalDaysInMonth) {
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if (dayOfWeek == weekDay) {
                count++
            }
        }

        return count
    }
}