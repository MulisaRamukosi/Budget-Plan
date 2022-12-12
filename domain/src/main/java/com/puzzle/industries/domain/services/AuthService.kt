package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.models.user.Account
import com.puzzle.industries.domain.models.user.AuthResponse
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    fun authResponseHandler(): SharedFlow<AuthResponse>
    fun alreadyAuthed(): Boolean
    fun getUserAccount(): Account
    suspend fun authUsingGmail()
    suspend fun authUsingFacebook()
    suspend fun authUsingEmailPassword(email: String, password: String)
    suspend fun authWithoutAccount()
    suspend fun forgotPassword(email: String)
    fun signOut()
}