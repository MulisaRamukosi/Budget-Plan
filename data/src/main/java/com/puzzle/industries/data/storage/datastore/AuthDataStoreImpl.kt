package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.data.util.config.DataStoreNameConfig
import com.puzzle.industries.domain.constants.AuthType
import com.puzzle.industries.domain.datastores.AuthDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.authDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameConfig.AUTH)

internal class AuthDataStoreImpl(private val context: Context): AuthDataStore {

    private val authedKey = booleanPreferencesKey(name = "authedKey")
    private val authTypeKey = stringPreferencesKey(name = "authTypeKey")

    override suspend fun setAuthState(authed: Boolean) {
        context.authDataStore.edit { settings ->
            settings[authedKey] = authed
        }
    }

    override fun isAuthed(): Flow<Boolean> = context.authDataStore.data.map {
        preferences -> preferences[authedKey] ?: false
    }

    override suspend fun setAuthType(authType: AuthType) {
        context.authDataStore.edit { settings ->
            settings[authTypeKey] = authType.name
        }
    }

    override fun authType(): Flow<AuthType> = context.authDataStore.data.map {
        preferences -> AuthType.valueOf(preferences[authTypeKey]?: AuthType.ANONYMOUS.name)
    }
}