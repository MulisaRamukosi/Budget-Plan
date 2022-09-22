package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.viewModels.PubSubViewModel
import javax.inject.Inject

class DebtViewModel @Inject constructor() : PubSubViewModel<Boolean>(initial = false)