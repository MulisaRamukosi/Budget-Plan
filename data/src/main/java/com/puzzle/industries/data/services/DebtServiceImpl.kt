package com.puzzle.industries.data.services

import com.puzzle.industries.data.util.calculationHelper.CalculationHelperDelegate
import com.puzzle.industries.data.util.calculationHelper.CalculationHelperDelegateImpl
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.datastores.DebtDataStore
import com.puzzle.industries.domain.models.DebtCheckResult
import com.puzzle.industries.domain.models.FrequencyDate
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import com.puzzle.industries.domain.repository.income.IncomeRepository
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.services.DebtService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.abs

internal class DebtServiceImpl constructor(
    private val calendarService: CalendarService,
    private val debtDataStore: DebtDataStore,
    private val incomeRepo: IncomeRepository,
    private val expenseGroupRepo: ExpenseGroupRepository,
) : DebtService, CalculationHelperDelegate by CalculationHelperDelegateImpl() {

    private val incomes: MutableStateFlow<List<Income>> =
        MutableStateFlow(value = emptyList())
    private val expenses: MutableStateFlow<List<Expense>> =
        MutableStateFlow(value = emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            launch {
                incomeRepo.read().response.collect {
                    incomes.value = it
                }
            }

            launch {
                expenseGroupRepo.read().response.collect {
                    expenses.value = it.flatMap { expenseGroup -> expenseGroup.expenses }
                }
            }
        }
    }

    override fun getDebtAllowedState(): Flow<Boolean> = debtDataStore.getSavedDebtOption()

    override fun getRemainingAmountAfterAllExpenses(expense: Expense, isAnExistingExpense: Boolean): DebtCheckResult {
        val calendar: Calendar = calendarService.getInstance()
        var month = calendar.get(Calendar.MONTH)
        var year: Int? = null

        if (expense.frequencyType == FrequencyType.ONCE_OFF) {
            val frequencyDate = FrequencyDate.parseDate(date = expense.frequencyWhen)
            month = frequencyDate.month
            year = frequencyDate.year
        }

        if (expense.frequencyType == FrequencyType.YEARLY) {
            val frequencyDate = FrequencyDate.parseDayMonth(date = expense.frequencyWhen)
            month = frequencyDate.month
        }

        return calculateDebtForMonthYear(
            month = Months.values()[month],
            year = year,
            expense = expense,
            isAModifiedExpense = isAnExistingExpense
        )
    }

    override fun willBeInDebtAfterAddingExpense(expense: Expense): DebtCheckResult {
        return handleExpenseDebtCalculations(expense = expense, isAModifiedExpense = false)
    }

    override fun willBeInDebtAfterModifyingExpense(expense: Expense): DebtCheckResult {
        return handleExpenseDebtCalculations(expense = expense, isAModifiedExpense = true)
    }

    override fun willBeInDebtAfterRemovingIncomes(vararg incomes: Income): DebtCheckResult {
        return handleIncomeDebtCalculations(removedIncomes = incomes.toList())
    }

    override fun willBeInDebtAfterModifyingIncome(income: Income): DebtCheckResult {
        return handleIncomeDebtCalculations(modifiedIncomes = listOf(income))
    }

    private fun handleIncomeDebtCalculations(
        removedIncomes: List<Income> = emptyList(),
        modifiedIncomes: List<Income> = emptyList()
    ): DebtCheckResult {
        val expense = Expense(
            expenseGroupId = UUID.randomUUID(),
            name = "",
            amount = 0.0,
            description = "",
            frequencyType = FrequencyType.MONTHLY,
            frequencyWhen = "1"
        )

        var result = handleRepetitiveExpenseCalculations(
            expense = expense,
            isAModifiedExpense = false,
            excludeIncomes = removedIncomes,
            modifiedIncomes = modifiedIncomes
        )

        val calendar = calendarService.getInstance()
        calendar.set(Calendar.DAY_OF_MONTH, 1)
        calendar.add(Calendar.MONTH, 11)

        val futureExpenses = expenses.value.filter {
            if (it.frequencyType == FrequencyType.ONCE_OFF) {
                val frequencyDate = FrequencyDate.parseDate(date = it.frequencyWhen)
                val expenseCalendar = Calendar.getInstance()
                expenseCalendar.set(frequencyDate.year, frequencyDate.month, frequencyDate.day)
                expenseCalendar.after(calendar)
            } else false
        }

        if (futureExpenses.isNotEmpty()) {
            futureExpenses.forEach { futureExpense ->
                val frequencyDate = FrequencyDate.parseDayMonth(date = futureExpense.frequencyWhen)
                val futureResult = calculateDebtForMonthYear(
                    month = Months.values()[frequencyDate.month],
                    expense = futureExpense,
                    isAModifiedExpense = true
                )

                if (futureResult.willBeInDebt) {
                    result = futureResult
                    return@forEach
                }
            }
        }

        return result
    }

    private fun handleExpenseDebtCalculations(
        expense: Expense,
        isAModifiedExpense: Boolean
    ): DebtCheckResult {
        return when (expense.frequencyType) {
            FrequencyType.ONCE_OFF -> {
                val frequencyDate = FrequencyDate.parseDate(date = expense.frequencyWhen)
                calculateDebtForMonthYear(
                    month = Months.values()[frequencyDate.month],
                    year = frequencyDate.year,
                    expense = expense,
                    isAModifiedExpense = isAModifiedExpense
                )
            }
            FrequencyType.YEARLY -> {
                val frequencyDate = FrequencyDate.parseDayMonth(date = expense.frequencyWhen)
                calculateDebtForMonthYear(
                    month = Months.values()[frequencyDate.month],
                    expense = expense,
                    isAModifiedExpense = isAModifiedExpense
                )
            }
            else -> handleRepetitiveExpenseCalculations(
                expense = expense,
                isAModifiedExpense = isAModifiedExpense
            )
        }
    }

    private fun handleRepetitiveExpenseCalculations(
        expense: Expense,
        isAModifiedExpense: Boolean,
        excludeIncomes: List<Income> = emptyList(),
        modifiedIncomes: List<Income> = emptyList()
    ): DebtCheckResult {
        val calendar = calendarService.getInstance()
        var result = DebtCheckResult(
            willBeInDebt = false,
            amount = 0.0,
            forMonth = Months.JANUARY,
            forYear = 2022
        )

        for (i in 0..10) {
            val calendarMonth = calendar.get(Calendar.MONTH)
            val month = Months.values()[calendarMonth]
            result = calculateDebtForMonthYear(
                month = month,
                expense = expense,
                isAModifiedExpense = isAModifiedExpense,
                excludeIncomes = excludeIncomes,
                modifiedIncomes = modifiedIncomes
            )

            if (result.willBeInDebt) return result

            calendar.add(Calendar.MONTH, 1)
        }

        return result
    }

    private fun calculateDebtForMonthYear(
        month: Months,
        expense: Expense,
        isAModifiedExpense: Boolean = false,
        year: Int? = null,
        excludeIncomes: List<Income> = emptyList(),
        modifiedIncomes: List<Income> = emptyList()
    ): DebtCheckResult {
        val calendar = calendarService.setMonthYear(month = month, year = year)

        val totalIncomes = incomes.value.sumOf {
            if (excludeIncomes.contains(it)) 0.0
            else {
                var currIncome = it
                modifiedIncomes.find { income -> income.id == it.id }?.let { income ->
                    currIncome = income
                }

                handleSumCalculation(
                    amount = currIncome.amount,
                    frequencyType = currIncome.frequencyType,
                    frequencyWhen = currIncome.frequencyWhen,
                    calendarInstance = calendar
                )
            }
        }
        val totalExpenses = expenses.value.sumOf {
            var currExpense = it

            if (isAModifiedExpense && it.id == expense.id) {
                currExpense = expense
            }

            handleSumCalculation(
                amount = currExpense.amount,
                frequencyType = currExpense.frequencyType,
                frequencyWhen = currExpense.frequencyWhen,
                calendarInstance = calendar
            )
        }

        val expenseCalculationForMonth = if (isAModifiedExpense) 0.0 //because its already included in the list of expenses
        else calculateAmountForSingleMonth(
            amount = expense.amount,
            frequencyType = expense.frequencyType,
            frequencyWhen = expense.frequencyWhen,
            calendarInstance = calendar
        )

        val debtAmount = totalIncomes - totalExpenses - expenseCalculationForMonth

        return DebtCheckResult(
            willBeInDebt = debtAmount < 0,
            amount = abs(debtAmount),
            forMonth = month,
            forYear = calendar.get(Calendar.YEAR)
        )
    }
}