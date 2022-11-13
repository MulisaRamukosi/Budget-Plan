package com.puzzle.industries.budgetplan.delegates.implementation

import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CurrencySymbolObserverDelegate
import com.puzzle.industries.budgetplan.util.configs.CurrencyConfig
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

class CurrencySymbolObserverDelegateImpl constructor(
    private val currencyPreferenceService: CountryCurrencyDataStore
) : CurrencySymbolObserverDelegate, CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    private val symbolFlow = MutableStateFlow(value = CurrencyConfig.DEFAULT_CURRENCY)
    override val currencySymbolFlow: StateFlow<String> = symbolFlow.asStateFlow()

    init {
        runCoroutine {
            currencyPreferenceService.getSelectedCurrencySymbol().collect { symbol ->
                symbolFlow.value = symbol
            }
        }
    }

}