package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.data.util.config.DataStoreNameConfig
import com.puzzle.industries.domain.datastores.IncomeDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.incomeDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameConfig.INCOME)

internal class IncomeDataStoreImpl(private val context: Context) : IncomeDataStore {

    private val autoDeleteKey = booleanPreferencesKey(name = "autoDeleteIncomeKey")

    override suspend fun saveAutoDeleteIncome(enabled: Boolean) {
        context.incomeDataStore.edit { settings ->
            settings[autoDeleteKey] = enabled
        }
    }

    override fun getAutoDeleteIncomeState(): Flow<Boolean> = context.incomeDataStore.data.map {
        preferences -> preferences[autoDeleteKey] ?: false
    }
}