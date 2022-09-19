package com.puzzle.industries.domain.services

interface CountryCurrencyPreferenceService {

    fun saveCurrencySymbol(symbol: String)
    fun getCurrencySymbol(): String

}