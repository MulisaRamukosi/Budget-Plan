package com.puzzle.industries.domain.models

import com.puzzle.industries.domain.constants.Constants

data class FrequencyDate(
    val day: Int,
    val month: Int,
    val year: Int,


    ) {
    override fun toString(): String {
        return Constants.DATE_FORMAT
            .replace("dd", day.toString())
            .replace("MM", month.toString())
            .replace("yyyy", year.toString())
    }
}

fun FrequencyDate.fromString(date: String): FrequencyDate {
    val dateInfo = date.split("/")
    return FrequencyDate(
        day = dateInfo[0].toInt(),
        month = dateInfo[1].toInt(),
        year = dateInfo[2].toInt()
    )
}
