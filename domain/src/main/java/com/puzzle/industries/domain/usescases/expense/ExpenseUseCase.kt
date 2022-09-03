package com.puzzle.industries.domain.usescases.expense

import com.puzzle.industries.domain.common.crud.*
import com.puzzle.industries.domain.models.expense.Expense

interface InsertExpenseUseCase : Insert<Expense>
interface ReadExpenseUseCase : Read<Expense>
interface UpdateExpenseUseCase : Update<Expense>
interface DeleteExpenseUseCase : Delete<Expense>

interface ExpenseUseCase {
    val create: InsertExpenseUseCase
    val read: ReadExpenseUseCase
    val update: UpdateExpenseUseCase
    val delete: DeleteExpenseUseCase
}