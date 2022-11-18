package com.puzzle.industries.domain.services

interface ExpenseAlarmService {
    suspend fun setAutoDeleteExpenseAlarm()
}