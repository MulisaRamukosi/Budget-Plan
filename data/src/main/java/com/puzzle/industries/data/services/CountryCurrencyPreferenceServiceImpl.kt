package com.puzzle.industries.data.services

import android.content.Context
import com.puzzle.industries.domain.services.CountryCurrencyPreferenceService

internal class CountryCurrencyPreferenceServiceImpl(context: Context) :
    PreferenceService(prefName = "currency", context = context), CountryCurrencyPreferenceService {

    private val currencySymbolKey = "csk"

    override fun saveCurrencySymbol(symbol: String) {
        editor.putString(currencySymbolKey, symbol).commit()
    }

    override fun getCurrencySymbol(): String {
        return preference.getString(currencySymbolKey, "R") ?: "R"
    }
}