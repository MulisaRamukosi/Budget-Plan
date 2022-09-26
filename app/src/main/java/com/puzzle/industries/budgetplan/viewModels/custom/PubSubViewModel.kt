package com.puzzle.industries.budgetplan.viewModels.custom

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

open class PubSubViewModel<T> @Inject constructor(private val initial: T) : ViewModel() {

    val pub: MutableLiveData<T> by lazy {
        MutableLiveData(initial)
    }

    val sub: LiveData<T> = pub
}