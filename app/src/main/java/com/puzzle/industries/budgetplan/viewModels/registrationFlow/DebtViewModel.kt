package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import com.puzzle.industries.budgetplan.viewModels.custom.PubSubViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class DebtViewModel @Inject constructor() : PubSubViewModel<Boolean>(initial = false)