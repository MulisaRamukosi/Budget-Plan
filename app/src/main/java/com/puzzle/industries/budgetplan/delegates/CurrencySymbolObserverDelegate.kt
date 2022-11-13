package com.puzzle.industries.budgetplan.delegates

import kotlinx.coroutines.flow.StateFlow

interface CurrencySymbolObserverDelegate {
    val currencySymbolFlow: StateFlow<String>
}