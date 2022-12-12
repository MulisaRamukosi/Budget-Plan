package com.puzzle.industries.data.delegates

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlin.coroutines.CoroutineContext

interface CoroutineDelegate {
    fun runCoroutine(context: CoroutineContext = Dispatchers.IO, block: suspend CoroutineScope.() -> Unit)
}