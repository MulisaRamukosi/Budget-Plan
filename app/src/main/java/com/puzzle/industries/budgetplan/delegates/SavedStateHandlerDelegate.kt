package com.puzzle.industries.budgetplan.delegates

import com.puzzle.industries.budgetplan.models.KeyStateFlowHandler
import kotlinx.coroutines.flow.StateFlow

interface SavedStateHandlerDelegate {

    fun <T> setStateFlowValue(key: String, value: T)
    fun <T> getStateFlow(key: String, initialValue: T): StateFlow<T>

    fun <T> registerKeyStateFlowHandler(
        key: String,
        initialValue: T,
        validationRule: (T) -> Boolean = { true }
    ): KeyStateFlowHandler<T>

}