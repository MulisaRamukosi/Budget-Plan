package com.puzzle.industries.budgetplan.viewModels

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class PubSubViewModel<T> constructor(private val initial: T) : ViewModel() {

    private val pub: MutableStateFlow<T> by lazy {
        MutableStateFlow(value = initial)
    }

    val sub: StateFlow<T> = pub.asStateFlow()

    fun publishValue(value: T){
        pub.value = value
    }
}