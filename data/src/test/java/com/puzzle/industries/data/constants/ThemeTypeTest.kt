package com.puzzle.industries.data.constants

import com.puzzle.industries.domain.constants.ThemeType
import org.junit.Assert
import org.junit.Test

class ThemeTypeTest {

    @Test
    fun testOrderOfThemeType(){
        val types = ThemeType.values()

        val expectedOrder = arrayOf(ThemeType.SYSTEM_DEPENDENT, ThemeType.LIGHT, ThemeType.DARK)

        Assert.assertArrayEquals(expectedOrder, types)
    }
}