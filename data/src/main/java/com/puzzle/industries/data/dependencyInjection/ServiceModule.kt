package com.puzzle.industries.data.dependencyInjection

import android.content.Context
import com.puzzle.industries.data.services.BPlanGenDayPreferenceServiceImpl
import com.puzzle.industries.data.services.CountryCurrencyPreferenceServiceImpl
import com.puzzle.industries.data.services.DebtPreferenceServiceImpl
import com.puzzle.industries.data.services.LaunchTrackingPreferenceServiceImpl
import com.puzzle.industries.domain.services.BPlanGenDayPreferenceService
import com.puzzle.industries.domain.services.CountryCurrencyPreferenceService
import com.puzzle.industries.domain.services.DebtPreferenceService
import com.puzzle.industries.domain.services.LaunchTrackingPreferenceService
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
    fun provideLaunchTrackingService(@ApplicationContext context: Context): LaunchTrackingPreferenceService =
        LaunchTrackingPreferenceServiceImpl(context = context)

    @Singleton
    @Provides
    fun provideCountryCurrencyPrefService(@ApplicationContext context: Context): CountryCurrencyPreferenceService =
        CountryCurrencyPreferenceServiceImpl(context = context)

    @Singleton
    @Provides
    fun provideDebtPrefService(@ApplicationContext context: Context): DebtPreferenceService =
        DebtPreferenceServiceImpl(context = context)

    @Singleton
    @Provides
    fun provideBPlanGenDayPrefService(@ApplicationContext context: Context): BPlanGenDayPreferenceService =
        BPlanGenDayPreferenceServiceImpl(context = context)
}