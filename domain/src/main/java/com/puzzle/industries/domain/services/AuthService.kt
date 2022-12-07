package com.puzzle.industries.domain.services

import com.puzzle.industries.domain.models.user.Account
import com.puzzle.industries.domain.models.user.AuthResponse
import kotlinx.coroutines.flow.SharedFlow
import kotlinx.coroutines.flow.StateFlow

interface AuthService {
    fun authResponseHandler(): SharedFlow<AuthResponse>
    fun alreadyAuthed(): Boolean
    fun getUserAccount(): Account
    fun initAuth()
    suspend fun authUsingGmail()
    suspend fun authUsingFacebook()
    fun signOut()
}