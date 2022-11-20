package com.puzzle.industries.data.services

import com.puzzle.industries.data.storage.database.dao.expense.ExpenseDao
import com.puzzle.industries.data.storage.database.dao.income.IncomeDao
import com.puzzle.industries.data.storage.database.entity.expense.ExpenseEntity
import com.puzzle.industries.data.storage.database.entity.income.IncomeEntity
import com.puzzle.industries.data.util.calculationHelper.CalculationHelperDelegate
import com.puzzle.industries.data.util.calculationHelper.CalculationHelperDelegateImpl
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.datastores.DebtDataStore
import com.puzzle.industries.domain.models.DebtCheckResult
import com.puzzle.industries.domain.models.FrequencyDate
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.services.DebtService
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.launch
import java.util.*
import kotlin.math.abs

internal class DebtServiceImpl constructor(
    private val calendarService: CalendarService,
    private val debtDataStore: DebtDataStore,
    private val incomeDao: IncomeDao,
    private val expenseDao: ExpenseDao
) : DebtService, CalculationHelperDelegate by CalculationHelperDelegateImpl() {

    private val incomes: MutableStateFlow<List<IncomeEntity>> =
        MutableStateFlow(value = emptyList())
    private val expenses: MutableStateFlow<List<ExpenseEntity>> =
        MutableStateFlow(value = emptyList())

    init {
        CoroutineScope(Dispatchers.IO).launch {
            async {
                incomeDao.read().collect {
                    incomes.value = it
                }
            }

            async {
                expenseDao.read().collect {
                    expenses.value = it
                }
            }


        }
    }

    override fun getDebtAllowedState(): Flow<Boolean> = debtDataStore.getSavedDebtOption()

    override fun willBeInDebtAfterAddingExpense(expense: Expense): DebtCheckResult {
        return handleExpenseDebtCalculations(expense = expense, isAModifiedExpense = false)
    }

    override fun willBeInDebtAfterModifyingExpense(expense: Expense): DebtCheckResult {
        return handleExpenseDebtCalculations(expense = expense, isAModifiedExpense = true)
    }

    override fun willBeInDebtAfterRemovingIncome(income: Income): DebtCheckResult {
        TODO("Not yet implemented")
    }

    override fun willBeInDebtAfterModifyingIncome(expense: Expense): Flow<DebtCheckResult> {
        TODO("Not yet implemented")
    }

    private fun handleExpenseDebtCalculations(
        expense: Expense,
        isAModifiedExpense: Boolean
    ): DebtCheckResult {
        return when (expense.frequencyType) {
            FrequencyType.ONCE_OFF, FrequencyType.YEARLY -> {
                val frequencyDate = FrequencyDate.parseDayMonth(date = expense.frequencyWhen)
                calculateDebtForMonth(
                    month = Months.values()[frequencyDate.month],
                    expense = expense,
                    isAModifiedExpense = isAModifiedExpense
                )
            }
            else -> {
                val calendar = calendarService.getInstance()
                var result = DebtCheckResult(
                    willBeInDebt = false,
                    debtAmount = 0.0,
                    forMonth = Months.JANUARY,
                    forYear = 2022
                )

                for (i in 0..10) {
                    val calendarMonth = calendar.get(Calendar.MONTH)
                    val month = Months.values()[calendarMonth]
                    result = calculateDebtForMonth(
                        month = month,
                        expense = expense,
                        isAModifiedExpense = isAModifiedExpense
                    )

                    if (result.willBeInDebt) return result

                    calendar.add(Calendar.MONTH, 1)
                }

                result
            }

        }
    }

    private fun calculateDebtForMonth(
        month: Months,
        expense: Expense,
        isAModifiedExpense: Boolean = false
    ): DebtCheckResult {
        val calendar = calendarService.setMonth(month = month)

        val totalIncomes = incomes.value.sumOf {
            handleSumCalculation(
                amount = it.amount,
                frequencyType = it.frequencyType,
                frequencyWhen = it.frequencyWhen,
                calendarInstance = calendar
            )
        }
        val totalExpenses = expenses.value.sumOf {
            var expenseAmount = it.amount

            if (isAModifiedExpense && it.id == expense.id) {
                expenseAmount = expense.amount
            }

            handleSumCalculation(
                amount = expenseAmount,
                frequencyType = it.frequencyType,
                frequencyWhen = it.frequencyWhen,
                calendarInstance = calendar
            )
        }

        val debtAmount = totalIncomes - totalExpenses - calculateAmountForSingleMonth(
            amount = expense.amount,
            frequencyType = expense.frequencyType,
            frequencyWhen = expense.frequencyWhen,
            calendarInstance = calendar
        )

        return DebtCheckResult(
            willBeInDebt = debtAmount < 0,
            debtAmount = abs(debtAmount),
            forMonth = month,
            forYear = calendar.get(Calendar.YEAR)
        )
    }
}