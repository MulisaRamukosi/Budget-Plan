package com.puzzle.industries.data.services

import android.content.Context
import com.puzzle.industries.domain.services.BPlanGenDayPreferenceService

internal class BPlanGenDayPreferenceServiceImpl(context: Context) :
    PreferenceService(prefName = "BPlanGenDay", context = context), BPlanGenDayPreferenceService {

    private val dayKey = "dk"

    override fun saveDay(day: Int) {
        editor.putInt(dayKey, day).commit()
    }

    override fun getDay(): Int = preference.getInt(dayKey, 1)

}