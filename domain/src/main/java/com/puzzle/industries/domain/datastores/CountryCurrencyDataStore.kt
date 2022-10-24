package com.puzzle.industries.domain.datastores

import kotlinx.coroutines.flow.Flow

interface CountryCurrencyDataStore {

    suspend fun saveCurrencySymbol(symbol: String)
    fun getCurrencySymbol(): Flow<String>

}