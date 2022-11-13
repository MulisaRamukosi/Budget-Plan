package com.puzzle.industries.data.constants

import com.puzzle.industries.domain.constants.Months
import org.junit.Assert
import org.junit.Test

class MonthsTest {

    @Test
    fun testOrderOfMonths(){
        val months = Months.values()

        val expected = arrayOf(
            Months.JANUARY,
            Months.FEBRUARY,
            Months.MARCH,
            Months.APRIL,
            Months.MAY,
            Months.JUNE,
            Months.JULY,
            Months.AUGUST,
            Months.SEPTEMBER,
            Months.OCTOBER,
            Months.NOVEMBER,
            Months.DECEMBER
        )

        Assert.assertArrayEquals(expected, months)
    }
}