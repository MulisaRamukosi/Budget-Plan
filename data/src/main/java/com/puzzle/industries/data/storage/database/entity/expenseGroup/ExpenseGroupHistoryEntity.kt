package com.puzzle.industries.data.storage.database.entity.expenseGroup

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.storage.database.constants.Entities
import com.puzzle.industries.data.storage.database.entity.BaseHistory
import com.puzzle.industries.domain.constants.Action
import java.util.*

@Entity(tableName = Entities.EXPENSE_GROUP_HISTORY)
internal data class ExpenseGroupHistoryEntity(
    @PrimaryKey
    override val id: UUID = UUID.randomUUID(),
    val expenseGroup: ExpenseGroupEntity,
    override val action: Action,
    override val reason: String,
    override val entryDate: Date = Date()
) : BaseHistory()