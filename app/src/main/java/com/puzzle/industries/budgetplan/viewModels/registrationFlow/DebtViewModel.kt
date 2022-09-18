package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class DebtViewModel @Inject constructor() : ViewModel() {

    private val debt: MutableLiveData<Boolean> by lazy {
        MutableLiveData(false)
    }

    fun debt(): LiveData<Boolean> {
        return debt
    }

    fun enableDebt(enabled: Boolean) {
        debt.value = enabled
    }

}