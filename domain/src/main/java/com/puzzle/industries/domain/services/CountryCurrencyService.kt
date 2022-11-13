package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.models.CountryCurrency

interface CountryCurrencyService {

    fun getDefaultCountryCurrency(): CountryCurrency
    fun getAllCountries(): List<CountryCurrency>
    fun getCountryCurrencyByCurrencyName(currency: String): CountryCurrency
    fun getCurrencySymbolByCurrencyName(currency: String): String
}