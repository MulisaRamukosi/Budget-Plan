package com.puzzle.industries.budgetplan.factory.viewModel.implementation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.AddEditExpenseViewModel
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.services.DebtService
import com.puzzle.industries.domain.services.MonthTotalAmountCalculatorService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

@Suppress("UNCHECKED_CAST")
class AddEditExpenseViewModelFactory @AssistedInject constructor(
    @Assisted owner: SavedStateRegistryOwner,
    private val debtService: DebtService,
    private val monthTotalAmountCalculatorService: MonthTotalAmountCalculatorService,
    private val countryCurrencyDataStore: CountryCurrencyDataStore,
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
        prevExpense = prevExpense,
        debtService = debtService,
        monthTotalAmountCalculatorService = monthTotalAmountCalculatorService,
        countryCurrencyDataStore = countryCurrencyDataStore
    ) as T
}