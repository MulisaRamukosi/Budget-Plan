package com.puzzle.industries.budgetplan.viewModels.custom

import androidx.lifecycle.ViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
import javax.inject.Inject

open class PubSubViewModel<T> @Inject constructor(private val initial: T) : ViewModel() {

    private val pub: MutableStateFlow<T> by lazy {
        MutableStateFlow(value = initial)
    }

    val sub: StateFlow<T> = pub.asStateFlow()

    fun publishValue(value: T){
        pub.value = value
    }
}