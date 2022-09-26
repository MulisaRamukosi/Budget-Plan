package com.puzzle.industries.data.database.typeConverters

import androidx.room.TypeConverter
import com.puzzle.industries.domain.constants.FrequencyType

internal class FrequencyConverter {

    @TypeConverter
    fun toString(frequency: FrequencyType): String {
        return frequency.name.lowercase()
    }

    @TypeConverter
    fun toFrequency(frequency: String): FrequencyType {
        return when(frequency){
            FrequencyType.YEARLY.name.lowercase() -> FrequencyType.YEARLY
            FrequencyType.MONTHLY.name.lowercase() -> FrequencyType.MONTHLY
            FrequencyType.WEEKLY.name.lowercase() -> FrequencyType.WEEKLY
            FrequencyType.DAILY.name.lowercase() -> FrequencyType.DAILY
            else -> FrequencyType.MONTHLY
        }
    }

}