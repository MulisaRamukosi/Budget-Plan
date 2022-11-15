package com.puzzle.industries.data.services

import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.FrequencyType.*
import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.models.FrequencyDate
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.services.MonthTotalAmountCalculatorService
import java.util.*

internal class MonthTotalAmountCalculatorServiceImpl(private val calendarService: CalendarService) :
    MonthTotalAmountCalculatorService {

    override fun calculateTotalIncomesForMonth(month: Months, incomes: List<Income>): Double {
        val calendarInstance = calendarService.addMonth(month = month)

        return incomes.sumOf { income ->
            handleSumCalculation(
                amount = income.amount,
                frequencyType = income.frequencyType,
                frequencyWhen = income.frequencyWhen,
                calendarInstance = calendarInstance
            )
        }
    }

    override fun calculateTotalExpensesForMonth(month: Months, expenses: List<Expense>): Double {
        val calendarInstance = calendarService.addMonth(month = month)

        return expenses.sumOf { expense ->
            handleSumCalculation(
                amount = expense.amount,
                frequencyType = expense.frequencyType,
                frequencyWhen = expense.frequencyWhen,
                calendarInstance = calendarInstance
            )
        }
    }

    private fun handleSumCalculation(
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
                calendar = calendarInstance
            )
        } else 0.0
    }

    private fun includeInCurrentMonth(
        frequencyType: FrequencyType,
        frequencyWhen: String,
        calendar: Calendar
    ): Boolean {
        return when (frequencyType) {
            ONCE_OFF -> {
                val date = FrequencyDate.parseDate(date = frequencyWhen)
                calendar.get(Calendar.MONTH) == date.month && calendar.get(Calendar.YEAR) == date.year
            }
            YEARLY -> {
                val date = FrequencyDate.parseDayMonth(date = frequencyWhen)
                calendar.get(Calendar.MONTH) == date.month
            }
            else -> true
        }
    }

    private fun calculateTotal(
        amount: Double,
        frequencyType: FrequencyType,
        calendar: Calendar
    ): Double {
        val totalDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)
        val totalWeeksInMonth = calendar.getActualMaximum(Calendar.WEEK_OF_MONTH)

        return when (frequencyType) {
            ONCE_OFF -> amount
            DAILY -> totalDaysInMonth * amount
            WEEKLY -> totalWeeksInMonth * amount
            MONTHLY -> amount
            YEARLY -> amount
        }
    }


}
