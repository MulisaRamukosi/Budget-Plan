package com.puzzle.industries.data.services

import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.services.CalendarService
import java.util.*

internal class CalendarServiceImpl : CalendarService {

    override fun getInstance(): Calendar = tryToGetCachedCalendarInstance()

    override fun setMonthYear(month: Months, year: Int?): Calendar {
        val instance = tryToGetCachedCalendarInstance()
        instance.set(Calendar.DAY_OF_MONTH, 1)
        val currentMonth = instance.get(Calendar.MONTH)
        val currentYear = instance.get(Calendar.YEAR)

        if (year != null && year > currentYear){
            instance.set(Calendar.YEAR, year)
        }

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

    override fun getTotalDaysForMonth(month: Months): Int {
        val calendarInstance = tryToGetCachedCalendarInstance()
        calendarInstance.set(Calendar.MONTH, month.ordinal)
        return calendarInstance.getActualMaximum(Calendar.DAY_OF_MONTH)
    }

    fun tryToGetCachedCalendarInstance(): Calendar {
        //TODO: implement logic to retrieve calendar from datastore
        return Calendar.getInstance()
    }
}