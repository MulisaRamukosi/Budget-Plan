package com.puzzle.industries.budgetplan.factory

import kotlinx.coroutines.CoroutineScope

object CoroutineScopeFactory {
    private var customScope: CoroutineScope? = null

    fun getScope(defaultScope: CoroutineScope) : CoroutineScope = customScope ?: defaultScope

}