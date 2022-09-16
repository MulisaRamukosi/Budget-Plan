package com.puzzle.industries.domain.services.launch

interface LaunchTrackingService {

    fun isFirstTimeLaunch(): Boolean
    fun updateToNotFirstTimeLaunch()

}