package com.puzzle.industries.domain.models

import com.puzzle.industries.domain.constants.DateConfigs
import java.text.SimpleDateFormat
import java.util.*

data class FrequencyDate(
    val day: Int,
    val month: Int,
    val year: Int,
) {
    fun formatToDayMonth(): String {
        val date = Calendar.getInstance().apply {
            set(year, month, day)
        }

        return SimpleDateFormat(DateConfigs.DAY_MONTH_FORMAT, Locale.US).format(date.time)
    }

    override fun toString(): String {
        val date = Calendar.getInstance().apply {
            set(year, month, day)
        }
        return SimpleDateFormat(DateConfigs.DATE_FORMAT, Locale.US).format(date.time)
    }

    companion object {
        fun parseDayMonth(date: String): FrequencyDate {
            val parsedDate = SimpleDateFormat(DateConfigs.DAY_MONTH_FORMAT, Locale.US).parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = parsedDate

            return FrequencyDate(
                day = calendar.get(Calendar.DAY_OF_MONTH),
                month = calendar.get(Calendar.MONTH),
                year = 0
            )
        }

        fun parseDate(date: String): FrequencyDate {
            val parsedDate = SimpleDateFormat(DateConfigs.DATE_FORMAT, Locale.US).parse(date)
            val calendar = Calendar.getInstance()
            calendar.time = parsedDate

            return FrequencyDate(
                day = calendar.get(Calendar.DAY_OF_MONTH),
                month = calendar.get(Calendar.MONTH),
                year = calendar.get(Calendar.YEAR)
            )
        }
    }
}
