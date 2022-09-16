package com.puzzle.industries.data.dependencyInjection

import android.content.Context
import com.puzzle.industries.data.services.launch.LaunchTrackingServiceImpl
import com.puzzle.industries.domain.services.launch.LaunchTrackingService
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
    fun provideLaunchTrackingService(@ApplicationContext context: Context): LaunchTrackingService =
        LaunchTrackingServiceImpl(context = context)

}