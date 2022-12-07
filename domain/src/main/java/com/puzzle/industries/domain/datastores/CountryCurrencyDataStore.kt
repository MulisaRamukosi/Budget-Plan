package com.puzzle.industries.domain.datastores

import com.puzzle.industries.domain.models.CountryCurrency
import com.puzzle.industries.domain.services.CountryCurrencyService
import kotlinx.coroutines.flow.Flow

interface CountryCurrencyDataStore {

    suspend fun saveCountryCurrency(countryCurrency: CountryCurrency)
    fun getSelectedCountry(): Flow<String>

}