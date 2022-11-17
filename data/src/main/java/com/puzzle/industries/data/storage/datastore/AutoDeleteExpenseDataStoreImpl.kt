package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.data.util.config.DataStoreNameConfig
import com.puzzle.industries.domain.datastores.AutoDeleteExpenseDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.autoDeleteReminderStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameConfig.EXPENSE)

internal class AutoDeleteExpenseDataStoreImpl(private val context: Context) : AutoDeleteExpenseDataStore{

    private val autoDeleteKey = booleanPreferencesKey(name = "autoDeleteExpenseKey")

    override suspend fun saveAutoDeleteExpense(enabled: Boolean) {
        context.autoDeleteReminderStore.edit { settings ->
            settings[autoDeleteKey] = enabled
        }
    }

    override fun getAutoDeleteExpenseState(): Flow<Boolean> = context.autoDeleteReminderStore.data.map {
        preferences -> preferences[autoDeleteKey] ?: false
    }
}