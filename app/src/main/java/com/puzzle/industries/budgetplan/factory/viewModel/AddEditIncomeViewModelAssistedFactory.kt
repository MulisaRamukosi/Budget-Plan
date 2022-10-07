package com.puzzle.industries.budgetplan.factory.viewModel

import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.domain.models.income.Income
import dagger.assisted.AssistedFactory

@AssistedFactory
interface AddEditIncomeViewModelAssistedFactory {
    fun create(owner: SavedStateRegistryOwner, prevIncome: Income?): AddEditIncomeViewModelFactory
}