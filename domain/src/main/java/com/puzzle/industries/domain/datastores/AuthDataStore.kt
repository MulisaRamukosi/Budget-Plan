package com.puzzle.industries.domain.datastores

import com.puzzle.industries.domain.constants.AuthType
import kotlinx.coroutines.flow.Flow

interface AuthDataStore {
    suspend fun setAuthState(authed: Boolean)
    fun isAuthed(): Flow<Boolean>

    suspend fun setAuthType(authType: AuthType)
    fun authType(): Flow<AuthType>
}