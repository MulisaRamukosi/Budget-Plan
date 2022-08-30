package com.puzzle.industries.domain.usescases.expense

import com.puzzle.industries.domain.common.Create
import com.puzzle.industries.domain.common.Delete
import com.puzzle.industries.domain.common.MultiDelete
import com.puzzle.industries.domain.common.ReadAll
import com.puzzle.industries.domain.models.expense.ExpenseHistory

interface CreateExpenseHistoryUseCase : Create<ExpenseHistory>
interface ReadExpenseHistoryUseCase : ReadAll<ExpenseHistory>
interface DeleteExpenseHistoryUseCase : Delete<ExpenseHistory>, MultiDelete<ExpenseHistory>

interface ExpenseUseCaseHistory {
    val create: CreateExpenseHistoryUseCase
    val read: ReadExpenseHistoryUseCase
    val delete: DeleteExpenseHistoryUseCase
}