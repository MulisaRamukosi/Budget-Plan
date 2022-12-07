package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.models.CountryCurrency
import kotlinx.coroutines.flow.Flow

interface CountryCurrencyService {

    fun getDefaultCountryCurrency(): CountryCurrency
    fun getAllCountries(): List<CountryCurrency>
    suspend fun saveCountryCurrency(countryCurrency: CountryCurrency)
    fun selectedCountry(): Flow<CountryCurrency>
    fun getCountryCurrencyByCountryName(country: String): CountryCurrency
    fun getCurrencySymbolByCountryName(country: String): String
}