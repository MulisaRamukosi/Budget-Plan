package com.puzzle.industries.budgetplan.delegates.implementation

import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CurrencySymbolObserverDelegate
import com.puzzle.industries.budgetplan.util.configs.CurrencyConfig
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import kotlinx.coroutines.flow.MutableStateFlow

class CurrencySymbolObserverDelegateImpl constructor(
    private val currencyPreferenceService: CountryCurrencyDataStore
) : CurrencySymbolObserverDelegate, CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    init {
        runCoroutine {
            currencyPreferenceService.getCurrencySymbol().collect { symbol ->
                currencySymbolFlow.value = symbol
            }
        }
    }

    override val currencySymbolFlow: MutableStateFlow<String>
        get() = MutableStateFlow(value = CurrencyConfig.DEFAULT_CURRENCY)
}