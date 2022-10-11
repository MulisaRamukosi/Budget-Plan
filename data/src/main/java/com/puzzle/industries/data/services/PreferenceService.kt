package com.puzzle.industries.data.services

import android.content.Context
import android.content.SharedPreferences
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.preferencesDataStore

open class PreferenceService(prefName: String) {

    protected val Context.preference: DataStore<Preferences> by preferencesDataStore(name = prefName)

}
