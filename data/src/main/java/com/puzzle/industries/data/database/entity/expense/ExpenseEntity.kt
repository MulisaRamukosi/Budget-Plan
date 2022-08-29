package com.puzzle.industries.data.database.entity.expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.database.constants.Entities
import java.util.*

@Entity(tableName = Entities.EXPENSE)
data class ExpenseEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val expenseGroupId: UUID,
    val name: String,
    val amount: Double,
    val frequency: String,
    val lastModifyDate: Date = Date()
)
