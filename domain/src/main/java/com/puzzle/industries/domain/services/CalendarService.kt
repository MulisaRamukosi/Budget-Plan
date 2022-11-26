package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.constants.Months
import java.util.Calendar

interface CalendarService {
    fun getInstance(): Calendar
    fun setMonthYear(month: Months, year: Int? = null): Calendar
}