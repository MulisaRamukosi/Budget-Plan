package com.puzzle.industries.data.services

import android.content.Context
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import com.puzzle.industries.domain.services.CountryCurrencyPreferenceService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

internal class CountryCurrencyPreferenceServiceImpl(private val context: Context) :
    PreferenceService(prefName = "currency"), CountryCurrencyPreferenceService {

    private val currencySymbolKey = stringPreferencesKey(name = "csk")

    override suspend fun saveCurrencySymbol(symbol: String) {
        context.preference.edit { settings ->
            settings[currencySymbolKey] = symbol
        }
    }

    override fun getCurrencySymbol(): Flow<String> = context.preference.data.map { preferences ->
        preferences[currencySymbolKey] ?: "R"
    }

}