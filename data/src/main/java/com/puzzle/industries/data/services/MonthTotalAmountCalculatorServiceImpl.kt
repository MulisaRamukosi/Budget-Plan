package com.puzzle.industries.data.services

import com.puzzle.industries.data.util.calculationHelper.CalculationHelperDelegate
import com.puzzle.industries.data.util.calculationHelper.CalculationHelperDelegateImpl
import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.services.MonthTotalAmountCalculatorService
import java.util.*

internal class MonthTotalAmountCalculatorServiceImpl(private val calendarService: CalendarService) :
    MonthTotalAmountCalculatorService,
    CalculationHelperDelegate by CalculationHelperDelegateImpl() {

    override fun calculateTotalIncomesForMonth(
        month: Months?,
        year: Int?,
        incomes: List<Income>
    ): Double {
        val calendarInstance = getCalendarInstance(month = month, year = year)

        return incomes.sumOf { income ->
            handleSumCalculation(
                amount = income.amount,
                frequencyType = income.frequencyType,
                frequencyWhen = income.frequencyWhen,
                calendarInstance = calendarInstance
            )
        }
    }

    override fun calculateTotalExpensesForMonth(
        month: Months?,
        year: Int?,
        expenses: List<Expense>
    ): Double {
        val calendarInstance = getCalendarInstance(month = month, year = year)

        return expenses.sumOf { expense ->
            handleSumCalculation(
                amount = expense.amount,
                frequencyType = expense.frequencyType,
                frequencyWhen = expense.frequencyWhen,
                calendarInstance = calendarInstance
            )
        }
    }

    override fun calculatePayableExpenseInAMonth(expense: Expense): Double =
        calculateAmountForSingleMonth(
            amount = expense.amount,
            frequencyType = expense.frequencyType,
            frequencyWhen = expense.frequencyWhen,
            calendarInstance = calendarService.getInstance()
        )


    private fun getCalendarInstance(month: Months?, year: Int?): Calendar {
        val calendarInstance =
            if (month == null) calendarService.getInstance() else calendarService.setMonth(month = month)

        calendarInstance.set(
            Calendar.DAY_OF_MONTH,
            1
        ) //to avoid issues if the day is 29 and we change year to a non leap year
        val calendarYear = calendarInstance.get(Calendar.YEAR)
        if (year != null && year > calendarYear) {
            calendarInstance.set(Calendar.YEAR, year)
        }

        return calendarInstance
    }
}
