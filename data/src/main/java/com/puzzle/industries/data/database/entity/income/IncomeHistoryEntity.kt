package com.puzzle.industries.data.database.entity.income

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.entity.BaseHistory
import java.util.*

@Entity(tableName = Entities.INCOME_HISTORY)
internal class IncomeHistoryEntity(
    @PrimaryKey
    override val id: UUID = UUID.randomUUID(),
    val oldAmount: Double,
    val newAmount: Double,
    val oldFrequency: String,
    val newFrequency: String,
    val oldTitle: String,
    val newTitle: String,
    override val reason: String,
    override val action: String,
    override val entryDate: Date = Date()
) : BaseHistory()
