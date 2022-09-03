package com.puzzle.industries.domain.usescases.expense

import com.puzzle.industries.domain.common.crud.*
import com.puzzle.industries.domain.models.expense.ExpenseHistory

interface InsertExpenseHistoryUseCase : Insert<ExpenseHistory>
interface ReadExpenseHistoryUseCase : Read<ExpenseHistory>
interface DeleteExpenseHistoryUseCase : Delete<ExpenseHistory>

interface ExpenseUseCaseHistory {
    val create: InsertExpenseHistoryUseCase
    val read: ReadExpenseHistoryUseCase
    val delete: DeleteExpenseHistoryUseCase
}