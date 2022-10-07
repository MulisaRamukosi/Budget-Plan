package com.puzzle.industries.budgetplan.models

import kotlinx.coroutines.flow.StateFlow

data class KeyStateFlowHandler<T>(
    val valueStateFlow: StateFlow<T>,
    val onValueChange: (T) -> Unit
){
    fun getValue(): T {
        return valueStateFlow.value
    }
}
