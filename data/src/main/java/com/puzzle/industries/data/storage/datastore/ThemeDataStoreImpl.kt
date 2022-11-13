package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.data.util.config.DataStoreNameConfig
import com.puzzle.industries.domain.constants.ThemeType
import com.puzzle.industries.domain.datastores.ThemeDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.themeStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameConfig.THEME)

class ThemeDataStoreImpl(private val context: Context) : ThemeDataStore {

    private val themeKey = intPreferencesKey(name = "themeKey")

    override suspend fun saveTheme(themeType: ThemeType) {
        context.themeStore.edit { settings ->
            settings[themeKey] = themeType.ordinal
        }
    }

    override fun getAllThemes(): List<ThemeType> {
        return ThemeType.values().toList()
    }

    override fun getSelectedTheme(): Flow<ThemeType> = context.themeStore.data.map { preferences ->
        val themeType: Int? = preferences[themeKey]
        if (themeType != null) ThemeType.values()[themeType]
        else ThemeType.SYSTEM_DEPENDENT
    }
}