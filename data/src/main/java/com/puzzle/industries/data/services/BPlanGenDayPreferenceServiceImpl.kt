package com.puzzle.industries.data.services

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.core.stringPreferencesKey
import com.puzzle.industries.domain.services.BPlanGenDayPreferenceService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class BPlanGenDayPreferenceServiceImpl(private val context: Context) :
    PreferenceService(prefName = "BPlanGenDay"), BPlanGenDayPreferenceService {

    private val dayKey = intPreferencesKey(name = "dk")

    override suspend fun saveDay(day: Int) {
        context.preference.edit { settings ->
            settings[dayKey] = day
        }
    }

    override fun getDay(): Flow<Int> = context.preference.data.map { preferences ->
        preferences[dayKey] ?: 1
    }

}