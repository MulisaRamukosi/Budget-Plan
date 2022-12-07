package com.puzzle.industries.budgetplan.factory.viewModel

import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.factory.viewModel.implementation.AuthViewModelFactory
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.AuthViewModel
import com.puzzle.industries.domain.services.AuthService
import dagger.assisted.AssistedFactory

@AssistedFactory
interface AuthViewModelAssistedFactory {
    fun create(owner: SavedStateRegistryOwner, authService: AuthService): AuthViewModelFactory
}