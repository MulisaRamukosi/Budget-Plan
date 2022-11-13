package com.puzzle.industries.data.dependencyInjection

import android.content.Context
import com.puzzle.industries.data.services.CountryCurrencyServiceImpl
import com.puzzle.industries.data.services.ReminderServiceImpl
import com.puzzle.industries.data.storage.datastore.*
import com.puzzle.industries.domain.datastores.*
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
    fun provideReminderService(@ApplicationContext context: Context): ReminderService =
        ReminderServiceImpl(context = context)

    @Singleton
    @Provides
    fun provideCountryCurrencyService(): CountryCurrencyService = CountryCurrencyServiceImpl()
}