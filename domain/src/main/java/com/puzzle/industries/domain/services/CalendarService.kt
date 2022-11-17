package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.constants.Months
import java.util.Calendar

interface CalendarService {
    fun getInstance(): Calendar
    fun setMonth(month: Months): Calendar
}