package com.puzzle.industries.data.storage.datastore

import com.puzzle.industries.domain.datastores.*

class PreferencesStoreImpl(
    override val autoDeleteExpensePref: AutoDeleteExpenseDataStore,
    override val bPlanGenDayPref: BPlanGenDayDataStore,
    override val currencyPref: CountryCurrencyDataStore,
    override val debtPref: DebtDataStore,
    override val themePref: ThemeDataStore
) : PreferencesStore