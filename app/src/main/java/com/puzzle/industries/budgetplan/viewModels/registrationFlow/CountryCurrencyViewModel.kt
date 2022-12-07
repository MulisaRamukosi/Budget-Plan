package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import androidx.lifecycle.SavedStateHandle
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.SavedStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.viewModels.PubSubViewModel
import com.puzzle.industries.domain.models.CountryCurrency
import com.puzzle.industries.domain.services.CountryCurrencyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import javax.inject.Inject

@HiltViewModel
class CountryCurrencyViewModel @Inject constructor(
    private val countryCurrencyService: CountryCurrencyService,
    private val savedStateHandle: SavedStateHandle
) : PubSubViewModel<CountryCurrency>(initial = countryCurrencyService.getDefaultCountryCurrency()),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl(),
    SavedStateHandlerDelegate by SavedStateHandlerDelegateImpl(savedStateHandle = savedStateHandle) {

    private val filterTextKey = "filterTextKey"

    val filterText by lazy {
        registerKeyStateFlowHandler(key = filterTextKey, initialValue = "")
    }

    private val _allCountries: MutableStateFlow<List<CountryCurrency>> =
        MutableStateFlow(value = emptyList())
    val allCountries: StateFlow<List<CountryCurrency>> = _allCountries

    fun getCountryCurrencyByCountryName(countryName: String): CountryCurrency =
        countryCurrencyService.getCountryCurrencyByCountryName(country = countryName)

    init {
        initFilterTextFlow()
    }

    private fun initFilterTextFlow() = runCoroutine {
        filterText.valueStateFlow.collectLatest {
            val filterWord = it.lowercase()
            _allCountries.value =
                countryCurrencyService.getAllCountries().filter { countryCurrency ->
                    countryCurrency.currency.lowercase().contains(filterWord) ||
                            countryCurrency.country.lowercase().contains(filterWord)
                }
        }
    }
}