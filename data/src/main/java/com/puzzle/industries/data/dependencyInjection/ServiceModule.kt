package com.puzzle.industries.data.dependencyInjection

import android.content.Context
import com.puzzle.industries.data.services.ReminderServiceImpl
import com.puzzle.industries.data.storage.datastore.*
import com.puzzle.industries.domain.datastores.BPlanGenDayDataStore
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.datastores.DebtDataStore
import com.puzzle.industries.domain.datastores.LaunchTrackingDataStore
import com.puzzle.industries.domain.services.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class ServiceModule {

    @Singleton
    @Provides
    fun provideLaunchTrackingService(@ApplicationContext context: Context): LaunchTrackingDataStore =
        LaunchTrackingDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideCountryCurrencyPrefService(@ApplicationContext context: Context): CountryCurrencyDataStore =
        CountryCurrencyDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideDebtPrefService(@ApplicationContext context: Context): DebtDataStore =
        DebtDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideBPlanGenDayPrefService(@ApplicationContext context: Context): BPlanGenDayDataStore =
        BPlanGenDayDataStoreImpl(context = context)

    @Singleton
    @Provides
    fun provideReminderService(@ApplicationContext context: Context): ReminderService =
        ReminderServiceImpl(context = context)
}