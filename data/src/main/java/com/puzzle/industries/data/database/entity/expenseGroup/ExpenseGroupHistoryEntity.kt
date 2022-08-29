package com.puzzle.industries.data.database.entity.expenseGroup

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.entity.BaseHistory
import java.util.*

@Entity(tableName = Entities.EXPENSE_GROUP_HISTORY)
data class ExpenseGroupHistoryEntity(
    @PrimaryKey
    override val id: UUID,
    val oldName: String,
    val newName: String,
    override val action: String,
    override val reason: String,
    override val entryDate: Date
) : BaseHistory()