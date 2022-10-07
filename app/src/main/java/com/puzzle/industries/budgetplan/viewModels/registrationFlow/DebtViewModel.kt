package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import com.puzzle.industries.budgetplan.viewModels.PubSubViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor() : PubSubViewModel<Boolean>(initial = false)