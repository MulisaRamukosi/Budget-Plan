package com.puzzle.industries.budgetplan.factory.viewModel.implementation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.AddEditExpenseGroupViewModel
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

class AddEditExpenseGroupViewModelFactory @AssistedInject constructor(
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted private val prevExpenseGroup: ExpenseGroup?
) : AbstractSavedStateViewModelFactory(owner, null) {
    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = AddEditExpenseGroupViewModel(savedStateHandle = handle, prevExpenseGroup = prevExpenseGroup) as T
}