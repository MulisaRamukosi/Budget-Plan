package com.puzzle.industries.domain.usescases.expenseGroup

import com.puzzle.industries.domain.common.*
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupHistory

interface CreateExpenseGroupHistoryUseCase : Create<ExpenseGroupHistory>
interface DeleteExpenseGroupHistoryUseCase : Delete<ExpenseGroupHistory>
interface ReadExpenseGroupHistoryUseCase : Read<ExpenseGroupHistory>

interface ExpenseGroupHistoryUseCase {
    val create: CreateExpenseGroupHistoryUseCase
    val delete: DeleteExpenseGroupHistoryUseCase
    val read: ReadExpenseGroupHistoryUseCase
}