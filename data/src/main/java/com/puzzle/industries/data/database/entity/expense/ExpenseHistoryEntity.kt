package com.puzzle.industries.data.database.entity.expense

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.entity.BaseHistory
import com.puzzle.industries.domain.constants.Action
import java.util.*

@Entity(tableName = Entities.EXPENSE_HISTORY)
internal data class ExpenseHistoryEntity (
    @PrimaryKey
    override val id: UUID = UUID.randomUUID(),
    val expense: ExpenseEntity,
    override val action: Action,
    override val reason: String,
    override val entryDate: Date = Date()
) : BaseHistory()