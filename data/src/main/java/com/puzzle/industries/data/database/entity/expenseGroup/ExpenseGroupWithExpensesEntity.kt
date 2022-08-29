package com.puzzle.industries.data.database.entity.expenseGroup

import androidx.room.Embedded
import androidx.room.Relation
import com.puzzle.industries.data.database.entity.expense.ExpenseEntity

data class ExpenseGroupWithExpensesEntity(
    @Embedded
    val expenseGroup: ExpenseGroupEntity,
    @Relation(
        parentColumn = "id",
        entityColumn = "expenseGroupId"
    )
    val expenses: List<ExpenseEntity>
)
