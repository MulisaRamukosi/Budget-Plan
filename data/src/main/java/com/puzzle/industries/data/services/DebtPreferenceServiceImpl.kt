package com.puzzle.industries.data.services

import android.content.Context
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import com.puzzle.industries.domain.services.DebtPreferenceService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class DebtPreferenceServiceImpl(private val context: Context) :
    PreferenceService(prefName = "Debt"), DebtPreferenceService {

    private val debtKey = booleanPreferencesKey("dk")

    override suspend fun saveAllowDebtOption(option: Boolean) {
        context.preference.edit { settings ->
            settings[debtKey] = option
        }
    }

    override fun getSavedDebtOption(): Flow<Boolean> = context.preference.data.map { preferences ->
        preferences[debtKey] ?: false
    }
}