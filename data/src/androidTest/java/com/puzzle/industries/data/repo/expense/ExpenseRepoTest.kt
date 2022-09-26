package com.puzzle.industries.data.repo.expense

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.data.repo.BaseRepoDeleteTest
import com.puzzle.industries.data.repo.BaseRepoInsertTest
import com.puzzle.industries.data.repo.BaseRepoUpdateTest
import com.puzzle.industries.data.repository.expense.ExpenseRepositoryImpl
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.repository.expense.ExpenseRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import java.util.*

private val testEntities = arrayOf(
    Expense(
        expenseGroupId = UUID.randomUUID(),
        name = "asdf",
        amount = 134.0,
        frequencyType = FrequencyType.MONTHLY,
        frequencyWhen = "",
        description = ""
    ),
    Expense(
        expenseGroupId = UUID.randomUUID(),
        name = "vcbc",
        amount = 189.0,
        frequencyType = FrequencyType.YEARLY,
        frequencyWhen = "",
        description = ""
    )
)

private val expenseMapper: ExpenseMapper = ExpenseMapper()

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        ExpenseRepoInsertTest::class,
        ExpenseRepoUpdateTest::class,
        ExpenseRepoDeleteTest::class
    ]
)
internal class ExpenseRepoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseRepoInsertTest :
    BaseRepoInsertTest<ExpenseRepository, Expense>(testEntities = testEntities) {
    override fun initRepo(): ExpenseRepository = ExpenseRepositoryImpl(
        expenseMapper = expenseMapper,
        expenseDao = db.expenseDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseRepoUpdateTest :
    BaseRepoUpdateTest<ExpenseRepository, Expense>(testEntities = testEntities) {
    override fun initRepo(): ExpenseRepository = ExpenseRepositoryImpl(
        expenseMapper = expenseMapper,
        expenseDao = db.expenseDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseRepoDeleteTest :
    BaseRepoDeleteTest<ExpenseRepository, Expense>(testEntities = testEntities) {
    override fun initRepo(): ExpenseRepository = ExpenseRepositoryImpl(
        expenseMapper = expenseMapper,
        expenseDao = db.expenseDao(),
        responseMessageFactory = responseMessageFactory
    )
}