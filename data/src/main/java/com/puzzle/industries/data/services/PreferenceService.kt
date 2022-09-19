package com.puzzle.industries.data.services

import android.content.Context
import android.content.SharedPreferences

open class PreferenceService(prefName: String, context: Context) {

    protected val preference: SharedPreferences = context.getSharedPreferences(prefName, Context.MODE_PRIVATE)
    protected val editor: SharedPreferences.Editor by lazy { preference.edit() }

}
