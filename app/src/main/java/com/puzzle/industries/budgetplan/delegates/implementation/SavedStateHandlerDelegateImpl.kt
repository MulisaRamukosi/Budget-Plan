package com.puzzle.industries.budgetplan.delegates.implementation

import androidx.lifecycle.SavedStateHandle
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.models.KeyStateFlowHandler
import kotlinx.coroutines.flow.StateFlow

class SavedStateHandlerDelegateImpl constructor(private val savedStateHandle: SavedStateHandle) :
    SavedStateHandlerDelegate {

    override fun <T> setStateFlowValue(key: String, value: T) {
        savedStateHandle[key] = value
    }

    override fun <T> getStateFlow(key: String, initialValue: T): StateFlow<T> {
        return savedStateHandle.getStateFlow(key = key, initialValue = initialValue)
    }

    override fun <T> registerKeyStateFlowHandler(
        key: String,
        initialValue: T,
        validationRule: (T) -> Boolean
    ): KeyStateFlowHandler<T> {

        return KeyStateFlowHandler(
            valueStateFlow = getStateFlow(
                key = key,
                initialValue = initialValue
            ),
            onValueChange = { newValue ->
                if (validationRule(newValue)) setStateFlowValue(key = key, value = newValue)
            }
        )
    }


}