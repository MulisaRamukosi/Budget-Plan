package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.stringPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.data.util.config.DataStoreNameConfig
import com.puzzle.industries.domain.constants.CountryCurrencyConfig
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.models.CountryCurrency
import com.puzzle.industries.domain.services.CountryCurrencyService
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.currencyStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameConfig.COUNTRY_CURRENCY)

internal class CountryCurrencyDataStoreImpl(private val context: Context) :
    CountryCurrencyDataStore {

    private val currencyCountryKey = stringPreferencesKey(name = "selectedCurrencyCountryKey")

    override suspend fun saveCountryCurrency(countryCurrency: CountryCurrency) {
        context.currencyStore.edit { settings ->
            settings[currencyCountryKey] = countryCurrency.country
        }
    }

    override fun getSelectedCountry(): Flow<String> = context.currencyStore.data.map { preferences ->
        preferences[currencyCountryKey] ?: CountryCurrencyConfig.DEFAULT_COUNTRY
    }

}