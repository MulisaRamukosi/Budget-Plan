package com.puzzle.industries.data.dependencyInjection

import android.content.Context
import com.puzzle.industries.data.storage.datastore.*
import com.puzzle.industries.domain.datastores.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class DataStoreModule {
    @Singleton
    @Provides
    fun provideLaunchTrackingDataStore(@ApplicationContext context: Context): LaunchTrackingDataStore =
        LaunchTrackingDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideCountryCurrencyDataStore(@ApplicationContext context: Context): CountryCurrencyDataStore =
        CountryCurrencyDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideDebtPrefDataStore(@ApplicationContext context: Context): DebtDataStore =
        DebtDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideBPlanGenDayPrefDataStore(@ApplicationContext context: Context): BPlanGenDayDataStore =
        BPlanGenDayDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideExpensePrefDataStore(@ApplicationContext context: Context): ExpenseDataStore =
        ExpenseDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideThemePrefDataStore(@ApplicationContext context: Context): ThemeDataStore =
        ThemeDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideIncomePrefDataStore(@ApplicationContext context: Context): IncomeDataStore =
        IncomeDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideAuthPrefDataStore(@ApplicationContext context: Context): AuthDataStore =
        AuthDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun providePreferencesStore(
        expenseDataStore: ExpenseDataStore,
        incomeDataStore: IncomeDataStore,
        bPlanGenDayDataStore: BPlanGenDayDataStore,
        countryCurrencyDataStore: CountryCurrencyDataStore,
        debtPreferencesStore: DebtDataStore,
        themePreferencesStore: ThemeDataStore
    ): PreferencesStore =
        PreferencesStoreImpl(
            expensePref = expenseDataStore,
            incomePref = incomeDataStore,
            bPlanGenDayPref = bPlanGenDayDataStore,
            currencyPref = countryCurrencyDataStore,
            debtPref = debtPreferencesStore,
            themePref = themePreferencesStore
        )
}