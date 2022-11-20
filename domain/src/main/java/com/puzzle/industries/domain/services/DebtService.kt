package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.models.DebtCheckResult
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.income.Income
import kotlinx.coroutines.flow.Flow

interface DebtService {
    fun getDebtAllowedState(): Flow<Boolean>
    fun willBeInDebtAfterAddingExpense(expense: Expense): DebtCheckResult
    fun willBeInDebtAfterModifyingExpense(expense: Expense): DebtCheckResult
    fun willBeInDebtAfterRemovingIncome(income: Income): DebtCheckResult
    fun willBeInDebtAfterModifyingIncome(expense: Expense): Flow<DebtCheckResult>
}