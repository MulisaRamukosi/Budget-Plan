package com.puzzle.industries.domain.datastores

import kotlinx.coroutines.flow.Flow

interface IncomeDataStore {
    suspend fun saveAutoDeleteIncome(enabled: Boolean)
    fun getAutoDeleteIncomeState(): Flow<Boolean>
}