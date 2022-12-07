package com.puzzle.industries.budgetplan.factory.viewModel.implementation

import androidx.lifecycle.AbstractSavedStateViewModelFactory
import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.AuthViewModel
import com.puzzle.industries.domain.services.AuthService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject

@Suppress("UNCHECKED_CAST")
class AuthViewModelFactory @AssistedInject constructor(
    @Assisted private val owner: SavedStateRegistryOwner,
    @Assisted private val authService: AuthService
): AbstractSavedStateViewModelFactory(owner, null) {

    override fun <T : ViewModel> create(
        key: String,
        modelClass: Class<T>,
        handle: SavedStateHandle
    ): T = AuthViewModel(savedStateHandle = handle, authService = authService) as T


}