package com.puzzle.industries.domain.datastores

import kotlinx.coroutines.flow.Flow

interface LaunchTrackingDataStore {

    fun isFirstTimeLaunch(): Flow<Boolean>
    suspend fun updateToNotFirstTimeLaunch()

}