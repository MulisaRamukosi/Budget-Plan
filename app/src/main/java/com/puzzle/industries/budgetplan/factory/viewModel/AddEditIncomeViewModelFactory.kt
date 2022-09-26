package com.puzzle.industries.budgetplan.factory.viewModel

import com.puzzle.industries.budgetplan.viewModels.budget.AddEditIncomeViewModel
import com.puzzle.industries.domain.models.income.Income
import dagger.assisted.AssistedFactory

@AssistedFactory
interface AddEditIncomeViewModelFactory {
    fun create(prevIncome: Income?): AddEditIncomeViewModel
}