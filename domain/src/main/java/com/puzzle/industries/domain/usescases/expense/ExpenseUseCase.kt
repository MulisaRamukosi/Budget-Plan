package com.puzzle.industries.domain.usescases.expense

import com.puzzle.industries.domain.common.*
import com.puzzle.industries.domain.models.expense.Expense

interface CreateExpenseUseCase : Create<Expense>
interface ReadExpenseUseCase : Read<Expense>
interface UpdateExpenseUseCase : Update<Expense>
interface DeleteExpenseUseCase : Delete<Expense>

interface ExpenseUseCase {
    val create: CreateExpenseUseCase
    val read: ReadExpenseUseCase
    val update: UpdateExpenseUseCase
    val delete: DeleteExpenseUseCase
}