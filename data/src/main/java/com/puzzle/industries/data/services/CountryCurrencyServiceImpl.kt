package com.puzzle.industries.data.services

import com.puzzle.industries.data.R
import com.puzzle.industries.data.util.Countries
import com.puzzle.industries.data.util.Countries.countries
import com.puzzle.industries.domain.constants.CountryCurrencyConfig
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.models.CountryCurrency
import com.puzzle.industries.domain.services.CountryCurrencyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.map

internal class CountryCurrencyServiceImpl(private val countryCurrencyDataStore: CountryCurrencyDataStore) :
    CountryCurrencyService {

    override fun getDefaultCountryCurrency(): CountryCurrency {
        return Countries.default_country
    }

    override fun getAllCountries(): List<CountryCurrency> = countries

    override suspend fun saveCountryCurrency(countryCurrency: CountryCurrency) {
        countryCurrencyDataStore.saveCountryCurrency(countryCurrency = countryCurrency)
    }

    override fun selectedCountry(): Flow<CountryCurrency> {
        return countryCurrencyDataStore.getSelectedCountry().map { countryName ->
            countries.firstOrNull { it.country == countryName} ?: Countries.default_country
        }
    }

    override fun getCountryCurrencyByCountryName(country: String): CountryCurrency =
        countries.firstOrNull { it.country == country } ?: countries[0]


    override fun getCurrencySymbolByCountryName(country: String): String =
        countries.firstOrNull { it.country == country }?.symbol
            ?: CountryCurrencyConfig.DEFAULT_CURRENCY
}