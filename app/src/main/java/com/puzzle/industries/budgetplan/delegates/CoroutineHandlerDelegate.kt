package com.puzzle.industries.budgetplan.delegates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Job
import kotlin.coroutines.CoroutineContext
import kotlin.coroutines.EmptyCoroutineContext

interface CoroutineHandlerDelegate {
    fun runCoroutine(context: CoroutineContext = EmptyCoroutineContext, action: suspend CoroutineScope.() -> Unit): Job
}