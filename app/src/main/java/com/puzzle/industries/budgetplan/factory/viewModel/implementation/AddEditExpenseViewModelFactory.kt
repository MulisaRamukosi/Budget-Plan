package com.puzzle.industries.budgetplan.factory.viewModel.implementation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.AddEditExpenseViewModel
import com.puzzle.industries.domain.models.expense.Expense
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

class AddEditExpenseViewModelFactory @AssistedInject constructor(
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted private val expenseGroupId: UUID,
    @Assisted private val prevExpense: Expense?
) : AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = AddEditExpenseViewModel(
        savedStateHandle = handle,
        expenseGroupId = expenseGroupId,
        prevExpense = prevExpense
    ) as T
}