package com.puzzle.industries.domain.usescases.expense

import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.usescases.base.UseCaseDelete
import com.puzzle.industries.domain.usescases.base.UseCaseInsert
import com.puzzle.industries.domain.usescases.base.UseCaseUpdate

interface InsertExpenseUseCase : UseCaseInsert<Expense>
interface UpdateExpenseUseCase : UseCaseUpdate<Expense>
interface DeleteExpenseUseCase : UseCaseDelete<Expense>

interface ExpenseUseCase {
    val create: InsertExpenseUseCase
    val update: UpdateExpenseUseCase
    val delete: DeleteExpenseUseCase
}