package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.viewModels.PubSubViewModel
import javax.inject.Inject

class IncomeInputViewModel @Inject constructor() : PubSubViewModel<Double>(initial = 0.0)