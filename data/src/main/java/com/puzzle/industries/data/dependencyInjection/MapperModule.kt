package com.puzzle.industries.data.dependencyInjection

import com.puzzle.industries.data.mapper.expense.ExpenseHistoryMapper
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupHistoryMapper
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupMapper
import com.puzzle.industries.data.mapper.income.IncomeHistoryMapper
import com.puzzle.industries.data.mapper.income.IncomeMapper
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class MapperModule {

    @Singleton
    @Provides
    fun provideIncomeMapper(): IncomeMapper = IncomeMapper()

    @Singleton
    @Provides
    fun provideIncomeHistoryMapper(): IncomeHistoryMapper = IncomeHistoryMapper()

    @Singleton
    @Provides
    fun provideExpenseMapper(): ExpenseMapper = ExpenseMapper()

    @Singleton
    @Provides
    fun provideExpenseHistoryMapper(): ExpenseHistoryMapper = ExpenseHistoryMapper()

    @Singleton
    @Provides
    fun provideExpenseGroupMapper(): ExpenseGroupMapper = ExpenseGroupMapper()

    @Singleton
    @Provides
    fun provideExpenseGroupHistoryMapper(): ExpenseGroupHistoryMapper = ExpenseGroupHistoryMapper()

}