package com.puzzle.industries.domain.repository.user

import com.puzzle.industries.domain.models.user.AuthResponse
import kotlinx.coroutines.flow.SharedFlow

interface AuthRepository {
    fun authResponseHandler(): SharedFlow<AuthResponse>
    suspend fun authWithoutAccount()
    suspend fun authUsingGmail()
    suspend fun authUsingFacebook()
    suspend fun authUsingEmailAndPassword(email: String, password: String)
    suspend fun forgotPassword(email: String)
}