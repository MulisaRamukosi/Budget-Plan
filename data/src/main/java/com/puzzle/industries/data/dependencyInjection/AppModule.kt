package com.puzzle.industries.data.dependencyInjection

import android.content.Context
import com.puzzle.industries.data.storage.database.AppDatabase
import com.puzzle.industries.data.notification.NotificationChannelService
import com.puzzle.industries.data.notification.implementation.NotificationChannelServiceImpl
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class AppModule {

    @Singleton
    @Provides
    fun provideDatabase(@ApplicationContext context: Context): AppDatabase =
        AppDatabase.buildDatabase(context = context)
}