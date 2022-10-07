package com.puzzle.industries.budgetplan.factory.viewModel

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.viewModels.budget.income.AddEditIncomeViewModel
import com.puzzle.industries.domain.models.income.Income
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class AddEditIncomeViewModelFactory @AssistedInject constructor(
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted private val prevIncome: Income?
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = AddEditIncomeViewModel(savedStateHandle = handle, prevIncome = prevIncome) as T

}