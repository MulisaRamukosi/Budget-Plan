package com.puzzle.industries.domain.services

import kotlinx.coroutines.flow.Flow

interface BPlanGenDayPreferenceService {
    suspend fun saveDay(day: Int)
    fun getDay(): Flow<Int>
}