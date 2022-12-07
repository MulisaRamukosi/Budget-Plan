package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.SavedStateHandlerDelegateImpl
import com.puzzle.industries.domain.models.user.AuthResponse
import com.puzzle.industries.domain.services.AuthService
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow
import javax.inject.Inject

class AuthViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val authService: AuthService) : ViewModel(),
    SavedStateHandlerDelegate by SavedStateHandlerDelegateImpl(savedStateHandle),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl(){

    val authStateFlow: SharedFlow<AuthResponse?> = authService.authResponseHandler()

    val onLoginWithGoogle: () -> Unit =  {
        runCoroutine {
            authService.authUsingGmail()
        }
    }

    val onLoginWithFacebook: () -> Unit = {

    }

    val onLoginWithEmailPassword: (email: String, password: String) -> Unit = { email, password ->

    }

    val onLoginWithoutAccount: () -> Unit = {

    }

}