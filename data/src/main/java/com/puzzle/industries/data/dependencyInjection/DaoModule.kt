package com.puzzle.industries.data.dependencyInjection

import com.puzzle.industries.data.storage.database.AppDatabase
import com.puzzle.industries.data.storage.database.dao.expense.ExpenseDao
import com.puzzle.industries.data.storage.database.dao.expense.ExpenseHistoryDao
import com.puzzle.industries.data.storage.database.dao.expenseGroup.ExpenseGroupDao
import com.puzzle.industries.data.storage.database.dao.expenseGroup.ExpenseGroupHistoryDao
import com.puzzle.industries.data.storage.database.dao.income.IncomeDao
import com.puzzle.industries.data.storage.database.dao.income.IncomeHistoryDao
import com.puzzle.industries.data.storage.database.dao.reminder.ReminderDao
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class DaoModule {

    @Singleton
    @Provides
    fun provideIncomeDao(database: AppDatabase): IncomeDao = database.incomeDao()

    @Singleton
    @Provides
    fun provideIncomeHistoryDao(database: AppDatabase): IncomeHistoryDao =
        database.incomeHistoryDao()

    @Singleton
    @Provides
    fun provideExpenseDao(database: AppDatabase): ExpenseDao = database.expenseDao()

    @Singleton
    @Provides
    fun provideExpenseHistoryDao(database: AppDatabase): ExpenseHistoryDao =
        database.expenseHistoryDao()

    @Singleton
    @Provides
    fun provideExpenseGroupDao(database: AppDatabase): ExpenseGroupDao = database.expenseGroupDao()

    @Singleton
    @Provides
    fun provideExpenseGroupHistoryDao(database: AppDatabase): ExpenseGroupHistoryDao =
        database.expenseGroupHistoryDao()
}