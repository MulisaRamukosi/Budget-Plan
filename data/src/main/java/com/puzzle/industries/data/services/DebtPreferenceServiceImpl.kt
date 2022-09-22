package com.puzzle.industries.data.services

import android.content.Context
import com.puzzle.industries.domain.services.DebtPreferenceService

internal class DebtPreferenceServiceImpl(context: Context) :
    PreferenceService(prefName = "Debt", context = context), DebtPreferenceService {

    private val debtKey = "dk"

    override fun saveAllowDebtOption(option: Boolean) {
        editor.putBoolean(debtKey, option).commit()
    }

    override fun getSavedDebtOption(): Boolean {
        return preference.getBoolean(debtKey, false)
    }
}