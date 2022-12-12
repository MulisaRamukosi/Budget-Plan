package com.puzzle.industries.budgetplan.factory.viewModel

import com.puzzle.industries.budgetplan.viewModels.registrationFlow.AuthViewModel
import com.puzzle.industries.domain.repository.user.AuthRepository
import dagger.assisted.AssistedFactory

@AssistedFactory
interface AuthViewModelAssistedFactory {
    fun create(authRepository: AuthRepository): AuthViewModel
}