package com.puzzle.industries.budgetplan.delegates.implementation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzle.industries.budgetplan.factory.CoroutineScopeFactory
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext

@HiltViewModel
class CoroutineHandlerDelegateImpl @Inject constructor(): CoroutineHandlerDelegate, ViewModel() {
    private val coroutineScope
        get() = CoroutineScopeFactory.getScope(defaultScope = viewModelScope)

    override fun runCoroutine(
        context: CoroutineContext,
        action: suspend CoroutineScope.() -> Unit
    ) = coroutineScope.launch(context) { action() }
}