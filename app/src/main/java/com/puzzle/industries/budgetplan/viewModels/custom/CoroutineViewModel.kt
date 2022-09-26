package com.puzzle.industries.budgetplan.viewModels.custom

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface CoroutineViewModel {

    fun runCoroutine(context: CoroutineContext = EmptyCoroutineContext, action: suspend CoroutineScope.() -> Unit): Job
}