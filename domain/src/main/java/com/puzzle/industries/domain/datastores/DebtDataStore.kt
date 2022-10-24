package com.puzzle.industries.domain.datastores

import kotlinx.coroutines.flow.Flow

interface DebtDataStore {

    suspend fun saveAllowDebtOption(option: Boolean)
    fun getSavedDebtOption(): Flow<Boolean>

}