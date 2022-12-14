package com.puzzle.industries.domain.datastores

interface PreferencesStore {
    val expensePref: ExpenseDataStore
    val incomePref: IncomeDataStore
    val bPlanGenDayPref: BPlanGenDayDataStore
    val currencyPref: CountryCurrencyDataStore
    val debtPref: DebtDataStore
    val themePref: ThemeDataStore
}