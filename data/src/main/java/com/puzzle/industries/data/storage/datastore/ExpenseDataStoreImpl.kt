package com.puzzle.industries.data.storage.datastore

import android.content.Context
import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.booleanPreferencesKey
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.preferencesDataStore
import com.puzzle.industries.data.util.config.DataStoreNameConfig
import com.puzzle.industries.domain.datastores.ExpenseDataStore
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map

private val Context.expenseDataStore: DataStore<Preferences> by preferencesDataStore(name = DataStoreNameConfig.EXPENSE)

internal class ExpenseDataStoreImpl(private val context: Context) : ExpenseDataStore{

    private val autoDeleteKey = booleanPreferencesKey(name = "autoDeleteExpenseKey")

    override suspend fun saveAutoDeleteExpense(enabled: Boolean) {
        context.expenseDataStore.edit { settings ->
            settings[autoDeleteKey] = enabled
        }
    }

    override fun getAutoDeleteExpenseState(): Flow<Boolean> = context.expenseDataStore.data.map {
        preferences -> preferences[autoDeleteKey] ?: false
    }
}