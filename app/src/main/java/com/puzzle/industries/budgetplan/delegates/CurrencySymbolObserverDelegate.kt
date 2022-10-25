package com.puzzle.industries.budgetplan.delegates

import kotlinx.coroutines.flow.MutableStateFlow

interface CurrencySymbolObserverDelegate {
    val currencySymbolFlow: MutableStateFlow<String>
}