package com.puzzle.industries.data.services

import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.services.CalendarService
import org.junit.Assert
import org.junit.Test
import org.mockito.Mockito
import org.mockito.Mockito.mockStatic
import java.util.*


class CalendarServiceTests {

    private val runTestUnderMockedCalendarCondition: (testScenario: (CalendarService) -> Unit) -> Unit =
        { scenario ->
            try {
                val calendarService = Mockito.mock(CalendarServiceImpl::class.java)
                val calendar = Calendar.getInstance()
                calendar.set(2022, 10, 22, 14, 18, 2)
                Mockito.`when`(calendarService.tryToGetCachedCalendarInstance()).thenReturn(calendar)

                scenario.invoke(calendarService)
            } catch (_: Exception) {
                val d = 12
            }
        }

    @Test
    fun setMonthYear_ProvidedYearHasPassed_CalendarCreatedWithYearAfterProvidedYear() =
        runTestUnderMockedCalendarCondition { calendarService ->
            val targetYear = 1998

            val instance = calendarService.setMonthYear(month = Months.SEPTEMBER, year = targetYear)

            Assert.assertTrue(instance.get(Calendar.YEAR) > targetYear)
        }

    @Test
    fun setMonthYear_ProvidedYearHasNotPassed_CalendarCreatedWithYearProvided() = runTestUnderMockedCalendarCondition { calendarService ->
        val targetYear = 2023

        val instance = calendarService.setMonthYear(month = Months.DECEMBER, year = targetYear)

        Assert.assertEquals(Months.DECEMBER.ordinal, instance.get(Calendar.MONTH))
        Assert.assertEquals(targetYear, instance.get(Calendar.YEAR))
    }

    @Test
    fun getTotalDaysForMonth_ProvidedMonthNovember_Returns30() =
        runTestUnderMockedCalendarCondition { calendarService ->
            val totalDaysInNovember = calendarService.getTotalDaysForMonth(month = Months.NOVEMBER)

            Assert.assertEquals(30, totalDaysInNovember)
        }

    @Test
    fun setMonthYear_ProvidedMonthBeforeTestMonth_CalendarCreatedWithMonthAndUpcomingYear() =
        runTestUnderMockedCalendarCondition { calendarService ->
            val instance = calendarService.setMonthYear(month = Months.JANUARY)

            Assert.assertEquals(0, instance.get(Calendar.MONTH))
            Assert.assertEquals(2023, instance.get(Calendar.YEAR))
        }

    @Test
    fun setMonthYear_ProvidedMonthAfterTestMonth_CalendarCreatedWithMonthAndCurrentTestYear() =
        runTestUnderMockedCalendarCondition { calendarService ->
            val instance = calendarService.setMonthYear(month = Months.DECEMBER)

            Assert.assertEquals(11, instance.get(Calendar.MONTH))
            Assert.assertEquals(2022, instance.get(Calendar.YEAR))
        }
}