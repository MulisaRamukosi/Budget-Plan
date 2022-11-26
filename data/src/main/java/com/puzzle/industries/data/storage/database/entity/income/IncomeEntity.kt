package com.puzzle.industries.data.storage.database.entity.income

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.puzzle.industries.data.storage.database.constants.Entities
import com.puzzle.industries.domain.constants.FrequencyType
import java.util.*

@Entity(tableName = Entities.INCOME)
internal data class IncomeEntity(
    @PrimaryKey
    val id: UUID = UUID.randomUUID(),
    val frequencyType: FrequencyType,
    val frequencyWhen: String,
    val amount: Double,
    val title: String,
    val description: String,
    val lastModifyDate: Date = Date()
){
    override fun equals(other: Any?): Boolean {
        return other is IncomeEntity && id == other.id
    }

    override fun hashCode(): Int {
        var result = id.hashCode()
        result = 31 * result + frequencyType.hashCode()
        result = 31 * result + frequencyWhen.hashCode()
        result = 31 * result + amount.hashCode()
        result = 31 * result + title.hashCode()
        result = 31 * result + description.hashCode()
        return result
    }
}