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
    fun provideAutoDeleteExpensePrefDataStore(@ApplicationContext context: Context): AutoDeleteExpenseDataStore =
        AutoDeleteExpenseDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideThemePrefDataStore(@ApplicationContext context: Context): ThemeDataStore =
        ThemeDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun providePreferencesStore(
        autoDeleteExpenseDataStore: AutoDeleteExpenseDataStore,
        bPlanGenDayDataStore: BPlanGenDayDataStore,
        countryCurrencyDataStore: CountryCurrencyDataStore,
        debtPreferencesStore: DebtDataStore,
        themePreferencesStore: ThemeDataStore
    ): PreferencesStore =
        PreferencesStoreImpl(
            autoDeleteExpensePref = autoDeleteExpenseDataStore,
            bPlanGenDayPref = bPlanGenDayDataStore,
            currencyPref = countryCurrencyDataStore,
            debtPref = debtPreferencesStore,
            themePref = themePreferencesStore
        )
}