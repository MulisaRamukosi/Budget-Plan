package com.puzzle.industries.data.dependencyInjection

import com.puzzle.industries.data.usecase.expense.ExpenseUseCaseImpl
import com.puzzle.industries.data.usecase.expenseGroup.ExpenseGroupUseCaseImpl
import com.puzzle.industries.data.usecase.income.IncomeUseCaseImpl
import com.puzzle.industries.data.usecase.reminder.ReminderUseCaseImpl
import com.puzzle.industries.domain.repository.expense.ExpenseHistoryRepository
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupHistoryRepository
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import com.puzzle.industries.domain.repository.income.IncomeHistoryRepository
import com.puzzle.industries.domain.repository.income.IncomeRepository
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.usescases.expense.ExpenseUseCase
import com.puzzle.industries.domain.usescases.expenseGroup.ExpenseGroupUseCase
import com.puzzle.industries.domain.usescases.income.IncomeUseCase
import com.puzzle.industries.domain.usescases.reminder.ReminderUseCase
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class UseCaseModule {

    @Provides
    @Singleton
    fun provideIncomeUseCase(
        incomeRepository: IncomeRepository,
        incomeHistoryRepository: IncomeHistoryRepository
    ): IncomeUseCase = IncomeUseCaseImpl(
        incomeRepository = incomeRepository,
        incomeHistoryRepository = incomeHistoryRepository
    )

    @Provides
    @Singleton
    fun provideExpenseUseCase(
        expenseRepository: ExpenseRepository,
        expenseHistoryRepository: ExpenseHistoryRepository,
        reminderRepository: ReminderRepository
    ): ExpenseUseCase = ExpenseUseCaseImpl(
        expenseRepository = expenseRepository,
        expenseHistoryRepository = expenseHistoryRepository,
        reminderRepository = reminderRepository
    )

    @Provides
    @Singleton
    fun provideExpenseGroupUseCase(
        expenseGroupRepository: ExpenseGroupRepository,
        expenseGroupHistoryRepository: ExpenseGroupHistoryRepository
    ): ExpenseGroupUseCase = ExpenseGroupUseCaseImpl(
        expenseGroupRepository = expenseGroupRepository,
        expenseGroupHistoryRepository = expenseGroupHistoryRepository
    )

    @Provides
    @Singleton
    fun provideReminderUseCase(
        reminderRepository: ReminderRepository,
        expenseRepository: ExpenseRepository
    ): ReminderUseCase = ReminderUseCaseImpl(
        reminderRepository = reminderRepository,
        expenseRepository = expenseRepository
    )
}