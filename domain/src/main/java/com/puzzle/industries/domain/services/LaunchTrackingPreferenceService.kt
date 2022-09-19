package com.puzzle.industries.domain.services

interface LaunchTrackingPreferenceService {

    fun isFirstTimeLaunch(): Boolean
    fun updateToNotFirstTimeLaunch()

}