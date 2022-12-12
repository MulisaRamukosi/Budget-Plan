package com.puzzle.industries.data.delegates.implementation

import com.puzzle.industries.data.delegates.CoroutineDelegate
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.launch
import kotlin.coroutines.CoroutineContext

class CoroutineDelegateImpl : CoroutineDelegate {

    override fun runCoroutine(context: CoroutineContext, block: suspend CoroutineScope.() -> Unit) {
        CoroutineScope(context = context).launch{
            block()
        }
    }
}