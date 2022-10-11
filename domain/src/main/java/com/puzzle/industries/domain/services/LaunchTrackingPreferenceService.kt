package com.puzzle.industries.domain.services

import kotlinx.coroutines.flow.Flow

interface LaunchTrackingPreferenceService {

    fun isFirstTimeLaunch(): Flow<Boolean>
    suspend fun updateToNotFirstTimeLaunch()

}