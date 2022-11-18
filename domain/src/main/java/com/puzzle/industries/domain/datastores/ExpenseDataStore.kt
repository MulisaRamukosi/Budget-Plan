package com.puzzle.industries.domain.datastores

import kotlinx.coroutines.flow.Flow

interface ExpenseDataStore {

    suspend fun saveAutoDeleteExpense(enabled: Boolean)
    fun getAutoDeleteExpenseState(): Flow<Boolean>
}