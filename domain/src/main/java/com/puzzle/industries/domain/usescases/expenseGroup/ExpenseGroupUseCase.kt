package com.puzzle.industries.domain.usescases.expenseGroup

import com.puzzle.industries.domain.common.*
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup

interface CreateExpenseGroupUseCase : Create<ExpenseGroup>
interface UpdateExpenseGroupUseCase : Update<ExpenseGroup>
interface DeleteExpenseGroupUseCase : Delete<ExpenseGroup>, MultiDelete<ExpenseGroup>
interface ReadExpenseGroupUseCase : ReadAll<ExpenseGroup>

interface ExpenseGroupUseCase {
    val create: CreateExpenseGroupUseCase
    val update: UpdateExpenseGroupUseCase
    val delete: DeleteExpenseGroupUseCase
    val read: ReadExpenseGroupUseCase
}