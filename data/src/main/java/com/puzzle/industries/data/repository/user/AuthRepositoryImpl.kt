package com.puzzle.industries.data.repository.user

import com.puzzle.industries.data.delegates.CoroutineDelegate
import com.puzzle.industries.data.delegates.implementation.CoroutineDelegateImpl
import com.puzzle.industries.domain.datastores.AuthDataStore
import com.puzzle.industries.domain.models.user.AuthResponse
import com.puzzle.industries.domain.repository.user.AuthRepository
import com.puzzle.industries.domain.services.AccountService
import com.puzzle.industries.domain.services.AuthService
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.collectLatest

class AuthRepositoryImpl(
    private val authService: AuthService,
    private val accountService: AccountService,
    private val authDataStore: AuthDataStore
) : AuthRepository,
    CoroutineDelegate by CoroutineDelegateImpl() {

    private val _authResponse: MutableSharedFlow<AuthResponse> = MutableSharedFlow()

    init {
        runCoroutine {
            authService.authResponseHandler().collectLatest {
                if (it.success){
                    authDataStore.setAuthState(authed = true)
                }
                _authResponse.emit(it)
            }
        }
    }



    override fun authResponseHandler(): SharedFlow<AuthResponse> = _authResponse

    override suspend fun authWithoutAccount() {
        authService.authWithoutAccount()
    }

    override suspend fun authUsingGmail() {
        authService.authUsingGmail()
    }

    override suspend fun authUsingFacebook() {
        authService.authUsingFacebook()
    }

    override suspend fun authUsingEmailAndPassword(email: String, password: String) {
        authService.authUsingEmailPassword(email = email, password = password)
    }

    override suspend fun forgotPassword(email: String) {
        authService.forgotPassword(email)
    }

}