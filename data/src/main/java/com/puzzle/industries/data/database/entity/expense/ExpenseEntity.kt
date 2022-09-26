package com.puzzle.industries.data.database.entity.expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.domain.constants.FrequencyType
import java.util.*

@Entity(tableName = Entities.EXPENSE)
internal data class ExpenseEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val frequencyType: FrequencyType,
    val frequencyWhen: String,
    val expenseGroupId: UUID,
    val name: String,
    val amount: Double,
    val description: String,
    val lastModifyDate: Date = Date()
)
