package com.puzzle.industries.data.constants

import com.puzzle.industries.domain.constants.WeekDays
import org.junit.Assert
import org.junit.Test

class WeekDaysTest {

    @Test
    fun testOrderOfWeekDays(){
        val days = WeekDays.values()

        val expected = arrayOf(
            WeekDays.SUNDAY,
            WeekDays.MONDAY,
            WeekDays.TUESDAY,
            WeekDays.WEDNESDAY,
            WeekDays.THURSDAY,
            WeekDays.FRIDAY,
            WeekDays.SATURDAY
        )

        Assert.assertArrayEquals(expected, days)
    }
}