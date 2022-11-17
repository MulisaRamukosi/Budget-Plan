package com.puzzle.industries.domain.datastores

interface PreferencesStore {
    val autoDeleteExpensePref: AutoDeleteExpenseDataStore
    val bPlanGenDayPref: BPlanGenDayDataStore
    val currencyPref: CountryCurrencyDataStore
    val debtPref: DebtDataStore
    val themePref: ThemeDataStore
}