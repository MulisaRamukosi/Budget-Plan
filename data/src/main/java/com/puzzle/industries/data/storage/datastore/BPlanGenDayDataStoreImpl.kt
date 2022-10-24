package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.domain.datastores.BPlanGenDayDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.bPlanGenDayStore: DataStore<Preferences> by preferencesDataStore(name = "BPlanGenDay")

internal class BPlanGenDayDataStoreImpl(private val context: Context) : BPlanGenDayDataStore {

    private val dayKey = intPreferencesKey(name = "dk")

    override suspend fun saveDay(day: Int) {
        context.bPlanGenDayStore.edit { settings ->
            settings[dayKey] = day
        }
    }

    override fun getDay(): Flow<Int> = context.bPlanGenDayStore.data.map { preferences ->
        preferences[dayKey] ?: 1
    }

}