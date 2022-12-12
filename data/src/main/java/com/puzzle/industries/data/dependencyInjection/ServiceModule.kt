package com.puzzle.industries.data.dependencyInjection

import android.content.Context
import com.puzzle.industries.data.services.*
import com.puzzle.industries.data.services.alarmManager.AlarmManagerService
import com.puzzle.industries.data.services.alarmManager.AlarmManagerServiceImpl
import com.puzzle.industries.data.storage.datastore.*
import com.puzzle.industries.domain.datastores.*
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import com.puzzle.industries.domain.repository.income.IncomeRepository
import com.puzzle.industries.domain.services.*
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import dagger.hilt.android.qualifiers.ActivityContext
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.android.scopes.ActivityRetainedScoped
import dagger.hilt.android.scopes.ActivityScoped
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
    fun provideCountryCurrencyService(countryCurrencyDataStore: CountryCurrencyDataStore): CountryCurrencyService =
        CountryCurrencyServiceImpl(countryCurrencyDataStore = countryCurrencyDataStore)

    @Singleton
    @Provides
    fun provideCalendarService(): CalendarService = CalendarServiceImpl()

    @Singleton
    @Provides
    fun provideMonthTotalAmountCalculatorService(calendarService: CalendarService): MonthTotalAmountCalculatorService =
        MonthTotalAmountCalculatorServiceImpl(calendarService = calendarService)

    @Singleton
    @Provides
    fun provideExpenseAlarmService(
        @ApplicationContext context: Context,
        alarmManagerService: AlarmManagerService,
        expenseDataStore: ExpenseDataStore,
        calendarService: CalendarService
    ): ExpenseAlarmService = ExpenseAlarmServiceImpl(
        context = context,
        alarmManagerService = alarmManagerService,
        expenseDataStore = expenseDataStore,
        calendarService = calendarService
    )

    @Singleton
    @Provides
    fun provideIncomeAlarmService(
        @ApplicationContext context: Context,
        alarmManagerService: AlarmManagerService,
        incomeDataStore: IncomeDataStore,
        calendarService: CalendarService
    ): IncomeAlarmService = IncomeAlarmServiceImpl(
        context = context,
        calendarService = calendarService,
        alarmManagerService = alarmManagerService,
        incomeDataStore = incomeDataStore
    )

    @Singleton
    @Provides
    fun provideDebtService(
        calendarService: CalendarService,
        debtDataStore: DebtDataStore,
        incomeRepo: IncomeRepository,
        expenseGroupRepo: ExpenseGroupRepository
    ): DebtService = DebtServiceImpl(
        calendarService = calendarService,
        debtDataStore = debtDataStore,
        incomeRepo = incomeRepo,
        expenseGroupRepo = expenseGroupRepo
    )

    @Singleton
    @Provides
    fun provideSubscriptionService(): SubscriptionService = SubscriptionServiceImpl()

    @Singleton
    @Provides
    fun provideLoggerService(): LoggerService = LoggerServiceImpl()

    @Singleton
    @Provides
    fun provideAccountService(): AccountService = AccountServiceImpl()

}

@Module
@InstallIn(ActivityComponent::class)
internal class ActivityServiceModule {
    @Provides
    @ActivityScoped
    fun provideAuthService(
        @ActivityContext context: Context,
        loggerService: LoggerService
    ): AuthService =
        AuthServiceImpl(context = context, loggerService = loggerService)
}

