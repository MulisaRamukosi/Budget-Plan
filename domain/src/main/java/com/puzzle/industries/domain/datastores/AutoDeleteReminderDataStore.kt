package com.puzzle.industries.domain.datastores

import kotlinx.coroutines.flow.Flow

interface AutoDeleteReminderDataStore {

    suspend fun saveAutoDeleteReminder(enabled: Boolean)
    fun getAutoDeleteReminderState(): Flow<Boolean>
}