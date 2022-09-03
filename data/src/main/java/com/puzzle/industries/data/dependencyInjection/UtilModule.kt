package com.puzzle.industries.data.dependencyInjection

import android.content.Context
import com.puzzle.industries.data.util.ResponseMessageFactory
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
class UtilModule {

    @Provides
    @Singleton
    fun provideResponseMessageFactory(@ApplicationContext context: Context): ResponseMessageFactory =
        ResponseMessageFactory(context)

}