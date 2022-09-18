package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import javax.inject.Inject

class IncomeInputViewModel @Inject constructor() : ViewModel() {

    private val income: MutableLiveData<Double> by lazy {
        MutableLiveData(0.0)
    }

    fun income(): LiveData<Double> {
        return income
    }

    fun setIncome(value: Double) {
        income.value = value
    }
}