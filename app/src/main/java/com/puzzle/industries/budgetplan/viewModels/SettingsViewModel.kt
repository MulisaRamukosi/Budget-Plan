package com.puzzle.industries.budgetplan.viewModels

import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.domain.constants.ThemeType
import com.puzzle.industries.domain.datastores.PreferencesStore
import com.puzzle.industries.domain.models.CountryCurrency
import com.puzzle.industries.domain.services.CountryCurrencyService
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

@HiltViewModel
class SettingsViewModel @Inject constructor(
    private val countryCurrencyService: CountryCurrencyService,
    private val settingsPreferences: PreferencesStore
) :
    ViewModel(),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    val selectedThemeOrdinal: Flow<Int> = settingsPreferences.themePref.getSelectedTheme().map { it.ordinal }
    fun onThemeSelected(type: ThemeType) = runCoroutine {
        settingsPreferences.themePref.saveTheme(themeType = type)
    }

    val allowDebtSelected: Flow<Boolean> = settingsPreferences.debtPref.getSavedDebtOption()
    fun onAllowDebtChange(allowDebt: Boolean) = runCoroutine {
        settingsPreferences.debtPref.saveAllowDebtOption(option = allowDebt)
    }

    val defaultCountryCurrency = countryCurrencyService.getDefaultCountryCurrency()
    val selectedCurrency: Flow<CountryCurrency> = settingsPreferences.currencyPref.getSelectedCurrencyName().map { currencyName ->
        countryCurrencyService.getCountryCurrencyByCurrencyName(currency = currencyName)
    }
    fun onSelectedCurrencyChange(currencyName: String) = runCoroutine {
        val countryCurrency = countryCurrencyService.getCountryCurrencyByCurrencyName(currency = currencyName)
        settingsPreferences.currencyPref.saveCountryCurrency(countryCurrency = countryCurrency)
    }

    val autoDeleteReminders: Flow<Boolean> = settingsPreferences.autoDeleteReminderPref.getAutoDeleteReminderState()
    fun onAutoDeleteRemindersChange(autoDelete: Boolean) = runCoroutine {
        settingsPreferences.autoDeleteReminderPref.saveAutoDeleteReminder(enabled = autoDelete)
    }

    val bPlanGenerationDay: Flow<Int> = settingsPreferences.bPlanGenDayPref.getDay()
    fun onBudgetPlanGenerationDay(day: Int) = runCoroutine {
        settingsPreferences.bPlanGenDayPref.saveDay(day = day)
    }
}