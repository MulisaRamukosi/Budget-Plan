package com.puzzle.industries.budgetplan.delegates.implementation

import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CurrencySymbolObserverDelegate
import com.puzzle.industries.budgetplan.util.configs.CurrencyConfig
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.services.CountryCurrencyService
import kotlinx.coroutines.flow.*

class CurrencySymbolObserverDelegateImpl constructor(
    private val countryCurrencyService: CountryCurrencyService
) : CurrencySymbolObserverDelegate, CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    private val symbolFlow = MutableStateFlow(value = CurrencyConfig.DEFAULT_CURRENCY)
    override val currencySymbolFlow: StateFlow<String> = symbolFlow.asStateFlow()

    init {
        runCoroutine {
            countryCurrencyService.selectedCountry().collectLatest { countryCurrency ->
                symbolFlow.value = countryCurrency.symbol
            }
        }
    }

}