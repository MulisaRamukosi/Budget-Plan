package com.puzzle.industries.budgetplan.factory

import com.puzzle.industries.domain.models.FrequencyDate
import java.util.*

object FrequencyDateFactory {
    fun createCurrentDate(): FrequencyDate{
        val calendar = Calendar.getInstance()
        val year = calendar.get(Calendar.YEAR)
        val month = calendar.get(Calendar.MONTH)
        val day = calendar.get(Calendar.DAY_OF_MONTH)

        return FrequencyDate(day = day, month = month, year = year)
    }
}