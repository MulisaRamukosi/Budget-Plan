package com.puzzle.industries.data.services

import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.services.CalendarService
import java.util.*

internal class CalendarServiceImpl : CalendarService {

    override fun getInstance(): Calendar = tryToGetCachedCalendarInstance()

    override fun addMonth(month: Months): Calendar {
        val instance = tryToGetCachedCalendarInstance()
        val currentMonth = instance.get(Calendar.MONTH)

        if (currentMonth > month.ordinal) {
            val monthsLeftTillEndOfYear = 11 - currentMonth
            val diff = monthsLeftTillEndOfYear + 1 + month.ordinal
            instance.add(Calendar.MONTH, diff)
        }
        else if (currentMonth < month.ordinal) {
            val diff = month.ordinal - currentMonth
            instance.add(Calendar.MONTH, diff)
        }

        return instance
    }

    private fun tryToGetCachedCalendarInstance(): Calendar {
        //TODO: implement logic to retrieve calendar from datastore
        return Calendar.getInstance()
    }
}