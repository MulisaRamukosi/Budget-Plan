package com.puzzle.industries.data.database.entity.expenseGroup

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.database.constants.Entities
import java.util.*

@Entity(tableName = Entities.EXPENSE_GROUP)
internal data class ExpenseGroupEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val name: String,
    val description: String,
    val colorId: String,
    val lastModifyDate: Date = Date()
)
