package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import android.util.Patterns
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.factory.viewModel.AuthViewModelAssistedFactory
import com.puzzle.industries.budgetplan.models.PasswordRequirement
import com.puzzle.industries.budgetplan.util.configs.PasswordConfig
import com.puzzle.industries.domain.models.user.AuthResponse
import com.puzzle.industries.domain.repository.user.AuthRepository
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.Job
import kotlinx.coroutines.flow.*

class AuthViewModel @AssistedInject constructor(@Assisted private val authRepo: AuthRepository) :
    ViewModel(),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    @Suppress("UNCHECKED_CAST")
    companion object {
        fun provideFactory(
            assistedFactory: AuthViewModelAssistedFactory,
            authRepo: AuthRepository
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(authRepository = authRepo) as T
            }
        }
    }

    private val _email: MutableStateFlow<String> = MutableStateFlow(value = "")
    val onEmailChange: (String) -> Unit = {
        _email.value = it.trim()
    }
    val email: StateFlow<String> = _email

    private val _password: MutableStateFlow<String> = MutableStateFlow(value = "")
    val onPasswordChange: (String) -> Unit = {
        _password.value = it
    }
    val password: StateFlow<String> = _password

    private val _passwordRequirements: MutableStateFlow<List<PasswordRequirement>> = MutableStateFlow(value = emptyList())
    val passwordRequirements: StateFlow<List<PasswordRequirement>> = _passwordRequirements

    private val _emailPasswordRequirementsMet: MutableStateFlow<Boolean> =
        MutableStateFlow(value = false)
    val emailPasswordRequirementsMet: StateFlow<Boolean> = _emailPasswordRequirementsMet

    private val _isAuthenticating: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val isAuthenticating: StateFlow<Boolean> = _isAuthenticating
    val authStateFlow: SharedFlow<AuthResponse?> = authRepo.authResponseHandler()

    private var _authJob: Job? = null

    val onLoginWithGoogle: () -> Unit = {
        _isAuthenticating.value = true
        _authJob = runCoroutine {
            authRepo.authUsingGmail()
        }
    }

    val onLoginWithFacebook: () -> Unit = {
        _isAuthenticating.value = true
        _authJob = runCoroutine {
            authRepo.authUsingFacebook()
        }
    }

    val onLoginWithEmailPassword: () -> Unit = {
        _isAuthenticating.value = true
        _authJob = runCoroutine {
            authRepo.authUsingEmailAndPassword(email = _email.value, password = _password.value)
        }
    }

    val onLoginWithoutAccount: () -> Unit = {
        _authJob = runCoroutine {
            authRepo.authWithoutAccount()
        }
    }

    val onForgotPassword: (String) -> Unit = { email ->
        _isAuthenticating.value = true
        _authJob = runCoroutine {
            authRepo.forgotPassword(email = email)
        }
    }

    val cancelAuth: () -> Unit = {
        _authJob?.cancel()
        _isAuthenticating.value = false
    }

    init {
        runCoroutine {
            authStateFlow.collectLatest {
                if (it != null) _isAuthenticating.value = false
            }
        }

        initEmailAndPasswordVerification()
    }

    private fun initEmailAndPasswordVerification() {
        runCoroutine {
            combine(_email, _password) { email, password ->
                val emailValid = Patterns.EMAIL_ADDRESS.matcher(email).matches()
                val passwordValid = validatePassword(password = password)
                emailValid && passwordValid
            }.distinctUntilChanged().collectLatest { allRequirementsMet ->
                _emailPasswordRequirementsMet.value = allRequirementsMet
            }
        }
    }

    private fun validatePassword(password: String): Boolean {
        val requirements: ArrayList<PasswordRequirement> = ArrayList()

        if (password.length < PasswordConfig.MIN_LENGTH) {
            requirements.add(
                PasswordRequirement(
                    stringId = R.string.password_minimum,
                    formatArgs = arrayOf(PasswordConfig.MIN_LENGTH)
                )
            )
        }
        var containsUpperCase = false
        var containsLowerCase = false
        var containsDigits = false
        var containsSymbol = false

        password.toCharArray().forEach { char ->
            if (char.isUpperCase()) containsUpperCase = true
            if (char.isLowerCase()) containsLowerCase = true
            if (char.isDigit()) containsDigits = true
            if (PasswordConfig.SYMBOLS.contains(char)) containsSymbol = true
        }

        if (!containsUpperCase) requirements.add(PasswordRequirement(stringId = R.string.password_requirements_uppercase))
        if (!containsLowerCase) requirements.add(PasswordRequirement(stringId = R.string.password_requirements_lowercase))
        if (!containsDigits) requirements.add(PasswordRequirement(stringId = R.string.password_requirements_digit))
        if (!containsSymbol) requirements.add(
            PasswordRequirement(
                stringId = R.string.password_requirements_symbol,
                formatArgs = arrayOf(PasswordConfig.SYMBOLS.joinToString(separator = ""))
            )
        )

        if (requirements.size > 0) {
            requirements.add(0, PasswordRequirement(R.string.password_requirements))
            _passwordRequirements.value = requirements
        }
        else {
            _passwordRequirements.value = emptyList()
        }

        return requirements.isEmpty()
    }


}