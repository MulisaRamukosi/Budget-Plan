package com.puzzle.industries.budgetplan.factory.viewModel.implementation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.AddEditExpenseViewModel
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.services.CountryCurrencyService
import com.puzzle.industries.domain.services.DebtService
import com.puzzle.industries.domain.services.MonthTotalAmountCalculatorService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

@Suppress("UNCHECKED_CAST")
class AddEditExpenseViewModelFactory @AssistedInject constructor(
    @Assisted owner: SavedStateRegistryOwner,
    @Assisted private val expenseGroupId: UUID,
    @Assisted private val prevExpense: Expense?,
    private val debtService: DebtService,
    private val countryCurrencyService: CountryCurrencyService,
    private val calendarService: CalendarService,
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
        countryCurrencyService = countryCurrencyService,
        calendarService = calendarService
    ) as T
}