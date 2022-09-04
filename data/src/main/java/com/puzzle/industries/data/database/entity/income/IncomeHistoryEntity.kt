package com.puzzle.industries.data.database.entity.income

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.entity.BaseHistory
import com.puzzle.industries.domain.constants.Action
import java.util.*

@Entity(tableName = Entities.INCOME_HISTORY)
internal class IncomeHistoryEntity(
    @PrimaryKey
    override val id: UUID = UUID.randomUUID(),
    val income: IncomeEntity,
    override val reason: String,
    override val action: Action,
    override val entryDate: Date = Date()
) : BaseHistory()
