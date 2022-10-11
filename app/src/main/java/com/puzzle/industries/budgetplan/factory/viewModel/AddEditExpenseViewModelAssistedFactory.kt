package com.puzzle.industries.budgetplan.factory.viewModel

import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.factory.viewModel.implementation.AddEditExpenseViewModelFactory
import com.puzzle.industries.domain.models.expense.Expense
import dagger.assisted.AssistedFactory
import java.util.*

@AssistedFactory
interface AddEditExpenseViewModelAssistedFactory {
    fun create(
        owner: SavedStateRegistryOwner,
        expenseGroupId: UUID,
        prevExpense: Expense?
    ): AddEditExpenseViewModelFactory
}