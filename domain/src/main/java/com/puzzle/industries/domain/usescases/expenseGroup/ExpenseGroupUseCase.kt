package com.puzzle.industries.domain.usescases.expenseGroup

import com.puzzle.industries.domain.common.crud.*
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup

interface InsertExpenseGroupUseCase : Insert<ExpenseGroup>
interface UpdateExpenseGroupUseCase : Update<ExpenseGroup>
interface DeleteExpenseGroupUseCase : Delete<ExpenseGroup>
interface ReadExpenseGroupUseCase : Read<ExpenseGroup>

interface ExpenseGroupUseCase {
    val create: InsertExpenseGroupUseCase
    val update: UpdateExpenseGroupUseCase
    val delete: DeleteExpenseGroupUseCase
    val read: ReadExpenseGroupUseCase
}