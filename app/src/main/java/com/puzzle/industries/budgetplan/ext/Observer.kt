package com.puzzle.industries.budgetplan.ext

import androidx.compose.runtime.*
import androidx.compose.ui.platform.LocalLifecycleOwner
import androidx.lifecycle.LiveData
import androidx.lifecycle.Observer

@Composable
fun <R, T : R> LiveData<T>.observeAsNullSafeState(initial: T): State<R> {
    val lifecycleOwner = LocalLifecycleOwner.current
    val state = remember { mutableStateOf(initial) }
    DisposableEffect(this, lifecycleOwner) {
        val observer = Observer<T> { state.value = it ?: initial }
        observe(lifecycleOwner, observer)
        onDispose { removeObserver(observer) }
    }
    return state
}