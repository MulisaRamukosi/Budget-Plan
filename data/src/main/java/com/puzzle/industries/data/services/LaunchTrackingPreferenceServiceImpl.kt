package com.puzzle.industries.data.services

import android.content.Context
import android.content.SharedPreferences
import com.puzzle.industries.domain.services.LaunchTrackingPreferenceService

internal class LaunchTrackingPreferenceServiceImpl(context: Context) : PreferenceService(
    prefName = "LaunchTrackingPref",
    context = context
), LaunchTrackingPreferenceService {

    private val launchTrackingKey: String = "LTK"

    override fun isFirstTimeLaunch(): Boolean {
        return preference.getBoolean(launchTrackingKey, true)
    }

    override fun updateToNotFirstTimeLaunch() {
        editor.putBoolean(launchTrackingKey, false).commit()
    }
}