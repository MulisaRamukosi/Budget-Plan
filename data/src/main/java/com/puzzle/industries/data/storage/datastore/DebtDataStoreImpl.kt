package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.domain.datastores.DebtDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.debtStore: DataStore<Preferences> by preferencesDataStore(name = "Debt")

internal class DebtDataStoreImpl(private val context: Context) : DebtDataStore {

    private val debtKey = booleanPreferencesKey("dk")

    override suspend fun saveAllowDebtOption(option: Boolean) {
        context.debtStore.edit { settings ->
            settings[debtKey] = option
        }
    }

    override fun getSavedDebtOption(): Flow<Boolean> = context.debtStore.data.map { preferences ->
        preferences[debtKey] ?: false
    }
}