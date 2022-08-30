package com.puzzle.industries.domain.usescases.expense

import com.puzzle.industries.domain.common.Create
import com.puzzle.industries.domain.common.Delete
import com.puzzle.industries.domain.common.Read
import com.puzzle.industries.domain.models.expense.ExpenseHistory

interface CreateExpenseHistoryUseCase : Create<ExpenseHistory>
interface ReadExpenseHistoryUseCase : Read<ExpenseHistory>
interface DeleteExpenseHistoryUseCase : Delete<ExpenseHistory>

interface ExpenseUseCaseHistory {
    val create: CreateExpenseHistoryUseCase
    val read: ReadExpenseHistoryUseCase
    val delete: DeleteExpenseHistoryUseCase
}