package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import com.puzzle.industries.budgetplan.viewModels.PubSubViewModel
import javax.inject.Inject

class BudgetPlanDayViewModel @Inject constructor(): PubSubViewModel<Int>(initial = 1)