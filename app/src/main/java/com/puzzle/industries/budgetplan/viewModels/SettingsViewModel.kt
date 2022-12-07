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
    val selectedCurrency: Flow<CountryCurrency> = countryCurrencyService.selectedCountry()

    fun onSelectedCurrencyChange(countryName: String) = runCoroutine {
        val countryCurrency = countryCurrencyService.getCountryCurrencyByCountryName(country = countryName)
        settingsPreferences.currencyPref.saveCountryCurrency(countryCurrency = countryCurrency)
    }

    val autoDeleteExpenses: Flow<Boolean> = settingsPreferences.expensePref.getAutoDeleteExpenseState()
    fun onAutoDeleteExpensesChange(autoDelete: Boolean) = runCoroutine {
        settingsPreferences.expensePref.saveAutoDeleteExpense(enabled = autoDelete)
    }

    val autoDeleteIncomes: Flow<Boolean> = settingsPreferences.incomePref.getAutoDeleteIncomeState()
    fun onAutoDeleteIncomesChange(autoDelete: Boolean) = runCoroutine {
        settingsPreferences.incomePref.saveAutoDeleteIncome(enabled = autoDelete)
    }

    val bPlanGenerationDay: Flow<Int> = settingsPreferences.bPlanGenDayPref.getDay()
    fun onBudgetPlanGenerationDay(day: Int) = runCoroutine {
        settingsPreferences.bPlanGenDayPref.saveDay(day = day)
    }
}