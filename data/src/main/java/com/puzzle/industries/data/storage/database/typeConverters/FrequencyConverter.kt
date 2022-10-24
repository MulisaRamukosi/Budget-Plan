package com.puzzle.industries.data.storage.database.typeConverters

import androidx.room.TypeConverter
import com.puzzle.industries.domain.constants.FrequencyType

internal class FrequencyConverter {

    @TypeConverter
    fun toString(frequency: FrequencyType): String {
        return frequency.name.lowercase()
    }

    @TypeConverter
    fun toFrequency(frequency: String): FrequencyType {
        return FrequencyType.valueOf(frequency.uppercase())
    }

}