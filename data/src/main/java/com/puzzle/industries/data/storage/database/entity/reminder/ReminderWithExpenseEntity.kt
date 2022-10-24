package com.puzzle.industries.data.storage.database.entity.reminder

import androidx.room.Embedded
import androidx.room.Relation
import com.puzzle.industries.data.storage.database.entity.expense.ExpenseEntity

internal data class ReminderWithExpenseEntity(
    @Embedded
    val reminder: ReminderEntity,
    @Relation(
        parentColumn = "expenseId",
        entityColumn = "id"
    )
    val expense: ExpenseEntity
)