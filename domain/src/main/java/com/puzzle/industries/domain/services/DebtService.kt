package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.models.DebtCheckResult
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.income.Income
import kotlinx.coroutines.flow.Flow

interface DebtService {
    fun getDebtAllowedState(): Flow<Boolean>
    fun getRemainingAmountAfterAllExpenses(expense: Expense, isAnExistingExpense: Boolean): DebtCheckResult
    fun willBeInDebtAfterAddingExpense(expense: Expense): DebtCheckResult
    fun willBeInDebtAfterModifyingExpense(expense: Expense): DebtCheckResult
    fun willBeInDebtAfterRemovingIncomes(vararg incomes: Income): DebtCheckResult
    fun willBeInDebtAfterModifyingIncome(income: Income): DebtCheckResult
}