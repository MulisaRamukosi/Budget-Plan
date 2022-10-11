package com.puzzle.industries.data.services

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.puzzle.industries.domain.services.LaunchTrackingPreferenceService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class LaunchTrackingPreferenceServiceImpl(private val context: Context) : PreferenceService(
    prefName = "LaunchTrackingPref"), LaunchTrackingPreferenceService {

    private val launchTrackingKey = booleanPreferencesKey(name = "LTK")

    override suspend fun updateToNotFirstTimeLaunch() {
        context.preference.edit { settings ->
            settings[launchTrackingKey] = false
        }
    }

    override fun isFirstTimeLaunch(): Flow<Boolean> = context.preference.data.map { preferences ->
        preferences[launchTrackingKey] ?: true
    }
}