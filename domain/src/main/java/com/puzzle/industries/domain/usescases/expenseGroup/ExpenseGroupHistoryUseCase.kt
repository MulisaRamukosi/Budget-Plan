package com.puzzle.industries.domain.usescases.expenseGroup

import com.puzzle.industries.domain.common.crud.*
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupHistory

interface InsertExpenseGroupHistoryUseCase : Insert<ExpenseGroupHistory>
interface DeleteExpenseGroupHistoryUseCase : Delete<ExpenseGroupHistory>
interface ReadExpenseGroupHistoryUseCase : Read<ExpenseGroupHistory>

interface ExpenseGroupHistoryUseCase {
    val create: InsertExpenseGroupHistoryUseCase
    val delete: DeleteExpenseGroupHistoryUseCase
    val read: ReadExpenseGroupHistoryUseCase
}