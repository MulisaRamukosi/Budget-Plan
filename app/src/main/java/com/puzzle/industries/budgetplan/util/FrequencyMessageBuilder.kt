package com.puzzle.industries.budgetplan.util

import androidx.compose.runtime.Composable
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.FrequencyDate


@Composable
fun buildFrequencyMessage(type: FrequencyType, message: String): String {
    val frequencyTypes = stringArrayResource(id = R.array.frequency)
    val frequencyName = frequencyTypes[type.ordinal]

    return when(type){
        FrequencyType.DAILY -> frequencyName
        FrequencyType.MONTHLY -> formatMonthlyDay(
            frequencyName = frequencyName,
            day = message.toInt()
        )
        FrequencyType.WEEKLY -> formatWeekDay(
            frequencyName = frequencyName,
            weekDay = WeekDays.valueOf(message)
        )
        FrequencyType.ONCE_OFF, FrequencyType.YEARLY -> formatFrequencyDate(
            frequencyName = frequencyName,
            date = FrequencyDate.fromString(date = message)
        )
    }
}

@Composable
private fun formatMonthlyDay(frequencyName: String, day: Int): String {
    val monthlyDay = formatDayWithSuffix(day = day)

    return stringResource(id = R.string.frequency_month_format, frequencyName, monthlyDay)
}

@Composable
private fun formatWeekDay(frequencyName: String, weekDay: WeekDays): String {
    val weekdays = stringArrayResource(id = R.array.days)
    val day = weekdays[weekDay.ordinal]

    return stringResource(id = R.string.frequency_message_format, frequencyName, day)
}

@Composable
private fun formatFrequencyDate(frequencyName: String, date: FrequencyDate) : String{
    val months = stringArrayResource(id = R.array.months)
    val monthDay = formatDayWithSuffix(day = date.day)
    val month = months[date.month]
    val fullDate = "$monthDay $month ${if (date.year == 0) "" else date.year}"

    return stringResource(id = R.string.frequency_message_format, frequencyName, fullDate)
}

@Composable
private fun formatDayWithSuffix(day: Int): String{
    val daySuffix = stringArrayResource(id = R.array.day_suffix)
    val daySuffixPos = when(day){
        1, 21, 31 -> 0
        2, 22 -> 1
        3, 23 -> 2
        else -> 3
    }
    return "$day${daySuffix[daySuffixPos]}"
}