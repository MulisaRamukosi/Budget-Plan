package com.puzzle.industries.domain.services

import kotlinx.coroutines.flow.Flow

interface CountryCurrencyPreferenceService {

    suspend fun saveCurrencySymbol(symbol: String)
    fun getCurrencySymbol(): Flow<String>

}