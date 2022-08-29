package com.puzzle.industries.data.database.typeConverters

import androidx.room.TypeConverter
import com.puzzle.industries.domain.constants.Frequency
import java.util.*

class FrequencyConverter {

    @TypeConverter
    fun toString(frequency: Frequency): String {
        return frequency.name.lowercase()
    }

    @TypeConverter
    fun toFrequency(frequency: String): Frequency {
        return when(frequency){
            Frequency.YEARLY.name.lowercase() -> Frequency.YEARLY
            Frequency.MONTHLY.name.lowercase() -> Frequency.MONTHLY
            Frequency.WEEKLY.name.lowercase() -> Frequency.WEEKLY
            Frequency.DAILY.name.lowercase() -> Frequency.DAILY
            else -> Frequency.MONTHLY
        }
    }



}