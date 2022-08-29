package com.puzzle.industries.data.database.entity.expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.entity.BaseHistory
import java.util.*

@Entity(tableName = Entities.EXPENSE_HISTORY)
data class ExpenseHistoryEntity (
    @PrimaryKey
    override val id: UUID,
    val expenseGroupId: UUID,
    val oldName: String,
    val newName: String,
    val oldAmount: Double,
    val newAmount: Double,
    val oldFrequency: String,
    val newFrequency: String,
    override val action: String,
    override val reason: String,
    override val entryDate: Date
) : BaseHistory()