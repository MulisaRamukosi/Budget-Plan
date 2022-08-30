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
    fun providesIncomeMapper(): IncomeMapper = IncomeMapper()

    @Singleton
    @Provides
    fun providesIncomeHistoryMapper(): IncomeHistoryMapper = IncomeHistoryMapper()

    @Singleton
    @Provides
    fun providesExpenseMapper(): ExpenseMapper = ExpenseMapper()

    @Singleton
    @Provides
    fun providesExpenseHistoryMapper(): ExpenseHistoryMapper = ExpenseHistoryMapper()

    @Singleton
    @Provides
    fun providesExpenseGroupMapper(): ExpenseGroupMapper = ExpenseGroupMapper()

    @Singleton
    @Provides
    fun providesExpenseGroupHistoryMapper(): ExpenseGroupHistoryMapper = ExpenseGroupHistoryMapper()

}