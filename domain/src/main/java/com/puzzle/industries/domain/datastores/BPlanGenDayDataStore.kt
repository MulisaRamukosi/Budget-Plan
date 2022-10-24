package com.puzzle.industries.domain.datastores

import kotlinx.coroutines.flow.Flow

interface BPlanGenDayDataStore {
    suspend fun saveDay(day: Int)
    fun getDay(): Flow<Int>
}