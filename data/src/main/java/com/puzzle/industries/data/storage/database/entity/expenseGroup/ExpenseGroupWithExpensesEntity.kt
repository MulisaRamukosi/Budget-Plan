package com.puzzle.industries.data.storage.database.entity.expenseGroup

import androidx.room.Embedded
import androidx.room.Relation
import com.puzzle.industries.data.storage.database.entity.expense.ExpenseEntity

internal data class ExpenseGroupWithExpensesEntity(
    @Embedded
    val expenseGroup: ExpenseGroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "expenseGroupId"
    )
    val expenses: List<ExpenseEntity>
)
