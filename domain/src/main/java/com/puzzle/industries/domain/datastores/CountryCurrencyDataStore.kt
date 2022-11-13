package com.puzzle.industries.domain.datastores

import com.puzzle.industries.domain.models.CountryCurrency
import kotlinx.coroutines.flow.Flow

interface CountryCurrencyDataStore {

    suspend fun saveCountryCurrency(countryCurrency: CountryCurrency)
    fun getSelectedCurrencySymbol(): Flow<String>
    fun getSelectedCurrencyName(): Flow<String>
}