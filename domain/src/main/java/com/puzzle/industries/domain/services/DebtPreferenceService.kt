package com.puzzle.industries.domain.services

import kotlinx.coroutines.flow.Flow

interface DebtPreferenceService {

    suspend fun saveAllowDebtOption(option: Boolean)
    fun getSavedDebtOption(): Flow<Boolean>

}