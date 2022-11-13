package com.puzzle.industries.data.services

import com.puzzle.industries.data.R
import com.puzzle.industries.domain.constants.CountryCurrencyConfig
import com.puzzle.industries.domain.models.CountryCurrency
import com.puzzle.industries.domain.services.CountryCurrencyService

internal class CountryCurrencyServiceImpl : CountryCurrencyService {

    private val countries = listOf(
        CountryCurrency(
            currency = "ZAR",
            country = "South Africa",
            symbol = "R",
            flagId = R.drawable.ic_flag_south_africa
        ),
        CountryCurrency(
            currency = "RWF",
            country = "Rwanda",
            symbol = "R₣",
            flagId = R.drawable.ic_flag_rwanda
        ),
        CountryCurrency(
            currency = "LSL",
            country = "Lesotho",
            symbol = "L",
            flagId = R.drawable.ic_flag_lesotho
        )
    )

    override fun getDefaultCountryCurrency(): CountryCurrency {
        return countries[0]
    }

    override fun getAllCountries(): List<CountryCurrency> = countries

    override fun getCountryCurrencyByCurrencyName(currency: String): CountryCurrency =
        countries.firstOrNull { it.currency == currency } ?: countries[0]


    override fun getCurrencySymbolByCurrencyName(currency: String): String =
        countries.firstOrNull { it.currency == currency }?.symbol
            ?: CountryCurrencyConfig.DEFAULT_CURRENCY
}