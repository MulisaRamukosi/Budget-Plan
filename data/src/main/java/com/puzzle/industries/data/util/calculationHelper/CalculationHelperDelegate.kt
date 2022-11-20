package com.puzzle.industries.data.util.calculationHelper

import com.puzzle.industries.domain.constants.FrequencyType
import java.util.*

interface CalculationHelperDelegate {
    fun handleSumCalculation(
        amount: Double,
        frequencyType: FrequencyType,
        frequencyWhen: String,
        calendarInstance: Calendar
    ): Double

    fun calculateAmountForSingleMonth(
        amount: Double,
        frequencyType: FrequencyType,
        frequencyWhen: String,
        calendarInstance: Calendar
    ): Double
}