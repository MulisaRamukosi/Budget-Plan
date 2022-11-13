package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import com.puzzle.industries.budgetplan.viewModels.PubSubViewModel
import com.puzzle.industries.domain.models.CountryCurrency
import com.puzzle.industries.domain.services.CountryCurrencyService
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor(private val countryCurrencyService: CountryCurrencyService) :
    PubSubViewModel<CountryCurrency>(initial = countryCurrencyService.getDefaultCountryCurrency()) {

    val allCountries: List<CountryCurrency>
        get() = countryCurrencyService.getAllCountries()

    fun getCountryCurrencyByCurrencyName(currencyName: String): CountryCurrency =
        countryCurrencyService.getCountryCurrencyByCurrencyName(currency = currencyName)
}