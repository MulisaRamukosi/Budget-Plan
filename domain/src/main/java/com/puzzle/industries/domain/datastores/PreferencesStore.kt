package com.puzzle.industries.domain.datastores

interface PreferencesStore {
    val autoDeleteReminderPref: AutoDeleteReminderDataStore
    val bPlanGenDayPref: BPlanGenDayDataStore
    val currencyPref: CountryCurrencyDataStore
    val debtPref: DebtDataStore
    val themePref: ThemeDataStore
}