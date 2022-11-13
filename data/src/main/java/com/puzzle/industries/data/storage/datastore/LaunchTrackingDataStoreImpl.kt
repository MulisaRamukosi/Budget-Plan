package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.data.util.config.DataStoreNameConfig
import com.puzzle.industries.domain.datastores.LaunchTrackingDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.launchStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameConfig.LAUNCH_TRACKING)

internal class LaunchTrackingDataStoreImpl(private val context: Context) : LaunchTrackingDataStore {

    private val launchTrackingKey = booleanPreferencesKey(name = "launchTrackingKey")

    override suspend fun updateToNotFirstTimeLaunch() {
        context.launchStore.edit { settings ->
            settings[launchTrackingKey] = false
        }
    }

    override fun isFirstTimeLaunch(): Flow<Boolean> = context.launchStore.data.map { preferences ->
        preferences[launchTrackingKey] ?: true
    }
}