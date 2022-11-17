package com.puzzle.industries.data.services

import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.FrequencyType.*
import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.FrequencyDate
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.services.MonthTotalAmountCalculatorService
import java.util.*

internal class MonthTotalAmountCalculatorServiceImpl(private val calendarService: CalendarService) :
    MonthTotalAmountCalculatorService {

    override fun calculateTotalIncomesForCurrentMonth(
        incomes: List<Income>
    ): Double {
        return incomes.sumOf { income ->
            handleSumCalculation(
                amount = income.amount,
                frequencyType = income.frequencyType,
                frequencyWhen = income.frequencyWhen,
                calendarInstance = calendarService.getInstance()
            )
        }
    }

    override fun calculateTotalExpensesForCurrentMonth(
        expenses: List<Expense>
    ): Double {
        return expenses.sumOf { expense ->
            handleSumCalculation(
                amount = expense.amount,
                frequencyType = expense.frequencyType,
                frequencyWhen = expense.frequencyWhen,
                calendarInstance = calendarService.getInstance()
            )
        }
    }

    override fun calculateTotalIncomesForMonth(month: Months, incomes: List<Income>): Double {
        val calendarInstance = calendarService.setMonth(month = month)

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
        val calendarInstance = calendarService.setMonth(month = month)

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
                frequencyWhen = frequencyWhen,
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
        frequencyWhen: String,
        calendar: Calendar
    ): Double {
        val totalDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        return when (frequencyType) {
            DAILY -> totalDaysInMonth * amount
            WEEKLY -> {
                val weekDay = WeekDays.valueOf(value = frequencyWhen).ordinal + 1
                val totalWeekDays = calculateTotalWeekDayInMonth(calendar = calendar, weekDay = weekDay)
                totalWeekDays * amount
            }
            else -> amount
        }
    }

    private fun calculateTotalWeekDayInMonth(calendar: Calendar, weekDay: Int): Int {
        var count = 0
        val totalDaysInMonth = calendar.getActualMaximum(Calendar.DAY_OF_MONTH)

        for (day in 1 .. totalDaysInMonth){
            calendar.set(Calendar.DAY_OF_MONTH, day)
            val dayOfWeek = calendar.get(Calendar.DAY_OF_WEEK)
            if(dayOfWeek == weekDay){
                count++
            }
        }

        return count
    }
}
