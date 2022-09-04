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

    private val incomeMapper: IncomeMapper = IncomeMapper()
    private val expenseMapper: ExpenseMapper = ExpenseMapper()
    private val expenseGroupMapper: ExpenseGroupMapper = ExpenseGroupMapper()

    @Singleton
    @Provides
    fun provideIncomeMapper(): IncomeMapper = incomeMapper

    @Singleton
    @Provides
    fun provideIncomeHistoryMapper(): IncomeHistoryMapper =
        IncomeHistoryMapper(incomeMapper = incomeMapper)

    @Singleton
    @Provides
    fun provideExpenseMapper(): ExpenseMapper = expenseMapper

    @Singleton
    @Provides
    fun provideExpenseHistoryMapper(): ExpenseHistoryMapper =
        ExpenseHistoryMapper(expenseMapper = expenseMapper)

    @Singleton
    @Provides
    fun provideExpenseGroupMapper(): ExpenseGroupMapper = expenseGroupMapper

    @Singleton
    @Provides
    fun provideExpenseGroupHistoryMapper(): ExpenseGroupHistoryMapper =
        ExpenseGroupHistoryMapper(expenseGroupMapper = expenseGroupMapper)

}