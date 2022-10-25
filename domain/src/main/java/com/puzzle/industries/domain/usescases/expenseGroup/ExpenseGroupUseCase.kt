package com.puzzle.industries.domain.usescases.expenseGroup

import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses
import com.puzzle.industries.domain.usescases.base.UseCaseDelete
import com.puzzle.industries.domain.usescases.base.UseCaseInsert
import com.puzzle.industries.domain.usescases.base.UseCaseRead
import com.puzzle.industries.domain.usescases.base.UseCaseUpdate

interface InsertExpenseGroupUseCase : UseCaseInsert<ExpenseGroup>
interface UpdateExpenseGroupUseCase : UseCaseUpdate<ExpenseGroup>
interface DeleteExpenseGroupUseCase : UseCaseDelete<ExpenseGroup>
interface ReadExpenseGroupUseCase : UseCaseRead<ExpenseGroupWithExpenses>

interface ExpenseGroupUseCase {
    val create: InsertExpenseGroupUseCase
    val update: UpdateExpenseGroupUseCase
    val delete: DeleteExpenseGroupUseCase
    val read: ReadExpenseGroupUseCase
}