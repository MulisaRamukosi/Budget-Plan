package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.currencyStore: DataStore<Preferences> by preferencesDataStore(name = "currency")

internal class CountryCurrencyDataStoreImpl(private val context: Context) :
    CountryCurrencyDataStore {

    private val currencySymbolKey = stringPreferencesKey(name = "csk")

    override suspend fun saveCurrencySymbol(symbol: String) {
        context.currencyStore.edit { settings ->
            settings[currencySymbolKey] = symbol
        }
    }

    override fun getCurrencySymbol(): Flow<String> = context.currencyStore.data.map { preferences ->
        preferences[currencySymbolKey] ?: "R"
    }

}