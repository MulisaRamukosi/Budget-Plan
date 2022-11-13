package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.data.util.config.DataStoreNameConfig
import com.puzzle.industries.domain.datastores.AutoDeleteReminderDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.autoDeleteReminderStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameConfig.REMINDER)

internal class AutoDeleteReminderDataStoreImpl(private val context: Context) : AutoDeleteReminderDataStore{

    private val autoDeleteKey = booleanPreferencesKey(name = "deleteReminderKey")

    override suspend fun saveAutoDeleteReminder(enabled: Boolean) {
        context.autoDeleteReminderStore.edit { settings ->
            settings[autoDeleteKey] = enabled
        }
    }

    override fun getAutoDeleteReminderState(): Flow<Boolean> = context.autoDeleteReminderStore.data.map {
        preferences -> preferences[autoDeleteKey] ?: false
    }
}