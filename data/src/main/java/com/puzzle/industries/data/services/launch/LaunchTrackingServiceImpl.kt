package com.puzzle.industries.data.services.launch

import android.content.Context
import android.content.SharedPreferences
import com.puzzle.industries.domain.services.launch.LaunchTrackingService

internal class LaunchTrackingServiceImpl(context: Context) : LaunchTrackingService {

    private val prefName = "LaunchTrackingPref"

    private val launchTrackingKey: String = "LTK"

    private val launchTrackingPreferences: SharedPreferences =
        context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    private val launchTrackingEditor: SharedPreferences.Editor by lazy {
        launchTrackingPreferences.edit()
    }

    override fun isFirstTimeLaunch(): Boolean {
        return launchTrackingPreferences.getBoolean(launchTrackingKey, true)
    }

    override fun updateToNotFirstTimeLaunch() {
        launchTrackingEditor.putBoolean(launchTrackingKey, false).commit()
    }
}