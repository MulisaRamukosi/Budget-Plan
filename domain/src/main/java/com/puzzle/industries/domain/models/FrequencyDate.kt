package com.puzzle.industries.domain.models

import com.puzzle.industries.domain.constants.Constants

data class FrequencyDate(
    val day: Int,
    val month: Int,
    val year: Int,
) {
    fun toDayMonthString(): String{
        return Constants.DATE_FORMAT
            .replace("dd", if(day < 10) "0$day" else day.toString())
            .replace("MM", if(month < 10) "0$month" else month.toString())
            .replace("/yyyy", "")
    }

    override fun toString(): String {
        return Constants.DATE_FORMAT
            .replace("dd", if(day < 10) "0$day" else day.toString())
            .replace("MM", if(month < 10) "0$month" else month.toString())
            .replace("yyyy", year.toString())
    }

    companion object {
        fun fromString(date: String): FrequencyDate {
            val dateInfo = date.split("/")
            return FrequencyDate(
                day = dateInfo[0].toInt(),
                month = dateInfo[1].toInt(),
                year = if(dateInfo.size == 3) dateInfo[2].toInt() else 0
            )
        }
    }
}

