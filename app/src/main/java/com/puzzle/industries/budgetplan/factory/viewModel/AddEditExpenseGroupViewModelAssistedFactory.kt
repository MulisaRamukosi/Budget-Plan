package com.puzzle.industries.budgetplan.factory.viewModel

import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.factory.viewModel.implementation.AddEditExpenseGroupViewModelFactory
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import dagger.assisted.AssistedFactory

@AssistedFactory
interface AddEditExpenseGroupViewModelAssistedFactory {
    fun create(
        owner: SavedStateRegistryOwner,
        prevExpenseGroup: ExpenseGroup?
    ): AddEditExpenseGroupViewModelFactory
}