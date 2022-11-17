package com.puzzle.industries.domain.datastores

import kotlinx.coroutines.flow.Flow

interface AutoDeleteExpenseDataStore {

    suspend fun saveAutoDeleteExpense(enabled: Boolean)
    fun getAutoDeleteExpenseState(): Flow<Boolean>
}