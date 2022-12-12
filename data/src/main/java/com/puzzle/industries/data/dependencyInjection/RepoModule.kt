package com.puzzle.industries.data.dependencyInjection

import com.puzzle.industries.data.mapper.expense.ExpenseHistoryMapper
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupHistoryMapper
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupMapper
import com.puzzle.industries.data.mapper.income.IncomeHistoryMapper
import com.puzzle.industries.data.mapper.income.IncomeMapper
import com.puzzle.industries.data.mapper.reminder.ReminderMapper
import com.puzzle.industries.data.repository.expense.ExpenseHistoryRepositoryImpl
import com.puzzle.industries.data.repository.expense.ExpenseRepositoryImpl
import com.puzzle.industries.data.repository.expenseGroup.ExpenseGroupHistoryRepositoryImpl
import com.puzzle.industries.data.repository.expenseGroup.ExpenseGroupRepositoryImpl
import com.puzzle.industries.data.repository.income.IncomeHistoryRepositoryImpl
import com.puzzle.industries.data.repository.income.IncomeRepositoryImpl
import com.puzzle.industries.data.repository.reminder.ReminderRepositoryImpl
import com.puzzle.industries.data.repository.user.AuthRepositoryImpl
import com.puzzle.industries.data.storage.database.dao.expense.ExpenseDao
import com.puzzle.industries.data.storage.database.dao.expense.ExpenseHistoryDao
import com.puzzle.industries.data.storage.database.dao.expenseGroup.ExpenseGroupDao
import com.puzzle.industries.data.storage.database.dao.expenseGroup.ExpenseGroupHistoryDao
import com.puzzle.industries.data.storage.database.dao.income.IncomeDao
import com.puzzle.industries.data.storage.database.dao.income.IncomeHistoryDao
import com.puzzle.industries.data.storage.database.dao.reminder.ReminderDao
import com.puzzle.industries.data.util.ResponseMessageFactory
import com.puzzle.industries.domain.datastores.AuthDataStore
import com.puzzle.industries.domain.repository.expense.ExpenseHistoryRepository
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupHistoryRepository
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import com.puzzle.industries.domain.repository.income.IncomeHistoryRepository
import com.puzzle.industries.domain.repository.income.IncomeRepository
import com.puzzle.industries.domain.repository.reminder.ReminderRepository
import com.puzzle.industries.domain.repository.user.AuthRepository
import com.puzzle.industries.domain.services.AccountService
import com.puzzle.industries.domain.services.AuthService
import com.puzzle.industries.domain.services.ReminderService
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.scopes.ActivityScoped
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
internal class RepoModule {

    @Singleton
    @Provides
    fun provideIncomeRepo(
        incomeMapper: IncomeMapper,
        incomeDao: IncomeDao,
        responseMessageFactory: ResponseMessageFactory
    ): IncomeRepository =
        IncomeRepositoryImpl(
            incomeMapper = incomeMapper,
            incomeDao = incomeDao,
            responseMessageFactory = responseMessageFactory
        )

    @Singleton
    @Provides
    fun provideIncomeHistoryRepo(
        incomeHistoryMapper: IncomeHistoryMapper,
        incomeHistoryDao: IncomeHistoryDao,
        responseMessageFactory: ResponseMessageFactory
    ): IncomeHistoryRepository = IncomeHistoryRepositoryImpl(
        incomeHistoryMapper = incomeHistoryMapper,
        incomeHistoryDao = incomeHistoryDao,
        responseMessageFactory = responseMessageFactory
    )

    @Singleton
    @Provides
    fun provideExpenseGroupRepo(
        expenseGroupMapper: ExpenseGroupMapper,
        expenseGroupDao: ExpenseGroupDao,
        responseMessageFactory: ResponseMessageFactory
    ): ExpenseGroupRepository = ExpenseGroupRepositoryImpl(
        expenseGroupMapper = expenseGroupMapper,
        expenseGroupDao = expenseGroupDao,
        responseMessageFactory = responseMessageFactory
    )

    @Singleton
    @Provides
    fun provideExpenseGroupHistoryRepo(
        expenseGroupHistoryMapper: ExpenseGroupHistoryMapper,
        expenseGroupHistoryDao: ExpenseGroupHistoryDao,
        responseMessageFactory: ResponseMessageFactory
    ): ExpenseGroupHistoryRepository = ExpenseGroupHistoryRepositoryImpl(
        expenseGroupHistoryMapper = expenseGroupHistoryMapper,
        expenseGroupHistoryDao = expenseGroupHistoryDao,
        responseMessageFactory = responseMessageFactory
    )

    @Singleton
    @Provides
    fun provideExpenseRepo(
        expenseMapper: ExpenseMapper,
        expenseDao: ExpenseDao,
        responseMessageFactory: ResponseMessageFactory
    ): ExpenseRepository = ExpenseRepositoryImpl(
        expenseMapper = expenseMapper,
        expenseDao = expenseDao,
        responseMessageFactory = responseMessageFactory
    )

    @Singleton
    @Provides
    fun provideExpenseHistoryRepo(
        expenseHistoryMapper: ExpenseHistoryMapper,
        expenseHistoryDao: ExpenseHistoryDao,
        responseMessageFactory: ResponseMessageFactory
    ): ExpenseHistoryRepository = ExpenseHistoryRepositoryImpl(
        expenseHistoryMapper = expenseHistoryMapper,
        expenseHistoryDao = expenseHistoryDao,
        responseMessageFactory = responseMessageFactory
    )

    @Singleton
    @Provides
    fun provideReminderRepo(
        reminderMapper: ReminderMapper,
        reminderDao: ReminderDao,
        reminderService: ReminderService
    ): ReminderRepository = ReminderRepositoryImpl(
        reminderMapper = reminderMapper,
        reminderDao = reminderDao,
        reminderService = reminderService
    )


}

@Module
@InstallIn(ActivityComponent::class)
internal class ActivityRetainedRepoModule {

    @Provides
    @ActivityScoped
    fun provideAuthRepo(
        authService: AuthService,
        accountService: AccountService,
        authDataStore: AuthDataStore
    ): AuthRepository = AuthRepositoryImpl(
        authService = authService,
        accountService = accountService,
        authDataStore = authDataStore
    )
}
