package com.puzzle.industries.data.dependencyInjection

import android.content.Context
import com.puzzle.industries.data.services.*
import com.puzzle.industries.data.services.alarmManager.AlarmManagerService
import com.puzzle.industries.data.services.alarmManager.AlarmManagerServiceImpl
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
    fun provideAlarmManagerService(@ApplicationContext context: Context): AlarmManagerService =
        AlarmManagerServiceImpl(context = context)

    @Singleton
    @Provides
    fun provideReminderService(
        @ApplicationContext context: Context,
        alarmManagerService: AlarmManagerService,
        calendarService: CalendarService
    ): ReminderService =
        ReminderServiceImpl(
            context = context,
            alarmManagerService = alarmManagerService,
            calendarService = calendarService
        )

    @Singleton
    @Provides
    fun provideCountryCurrencyService(): CountryCurrencyService = CountryCurrencyServiceImpl()

    @Singleton
    @Provides
    fun provideCalendarService(): CalendarService = CalendarServiceImpl()

    @Singleton
    @Provides
    fun provideMonthTotalAmountCalculatorService(calendarService: CalendarService): MonthTotalAmountCalculatorService =
        MonthTotalAmountCalculatorServiceImpl(calendarService = calendarService)

    @Singleton
    @Provides
    fun provideAutoDeleteExpensesAlarmService(
        @ApplicationContext context: Context,
        alarmManagerService: AlarmManagerService,
        autoDeleteExpenseDataStore: AutoDeleteExpenseDataStore,
        calendarService: CalendarService
    ): AutoDeleteExpensesAlarmService = AutoDeleteExpensesAlarmServiceImpl(
        context = context,
        alarmManagerService = alarmManagerService,
        autoDeleteExpenseDataStore = autoDeleteExpenseDataStore,
        calendarService = calendarService
    )
}