package com.puzzle.industries.data.repo.expense

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.mapper.expense.ExpenseHistoryMapper
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.data.repo.BaseRepoDeleteTest
import com.puzzle.industries.data.repo.BaseRepoInsertTest
import com.puzzle.industries.data.repo.BaseRepoReadTest
import com.puzzle.industries.data.repository.expense.ExpenseHistoryRepositoryImpl
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.expense.ExpenseHistory
import com.puzzle.industries.domain.repository.expense.ExpenseHistoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import java.util.*

private val testEntities = arrayOf(
    ExpenseHistory(
        expense = Expense(
            expenseGroupId = UUID.randomUUID(),
            name = "asdf",
            amount = 134.0,
            frequencyType = FrequencyType.MONTHLY,
            frequencyWhen = "1",
            description = ""
        ),
        action = Action.CREATE,
        reason = "some reason"
    ),
    ExpenseHistory(
        expense = Expense(
            expenseGroupId = UUID.randomUUID(),
            name = "vcbc",
            amount = 189.0,
            frequencyType = FrequencyType.YEARLY,
            frequencyWhen = "",
            description = ""
        ),
        action = Action.CREATE,
        reason = "some reason"
    )
)
private val expenseHistoryMapper: ExpenseHistoryMapper =
    ExpenseHistoryMapper(expenseMapper = ExpenseMapper())

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        ExpenseHistoryDaoInsertTest::class,
        ExpenseHistoryDaoReadTest::class,
        ExpenseHistoryDaoDeleteTest::class
    ]
)
internal class ExpenseHistoryRepoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoInsertTest :
    BaseRepoInsertTest<ExpenseHistoryRepository, ExpenseHistory>(testEntities = testEntities) {
    override fun initRepo(): ExpenseHistoryRepository = ExpenseHistoryRepositoryImpl(
        expenseHistoryMapper = expenseHistoryMapper,
        expenseHistoryDao = db.expenseHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoReadTest :
    BaseRepoReadTest<ExpenseHistoryRepository, ExpenseHistory>(testEntities = testEntities) {
    override fun initRepo(): ExpenseHistoryRepository = ExpenseHistoryRepositoryImpl(
        expenseHistoryMapper = expenseHistoryMapper,
        expenseHistoryDao = db.expenseHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoDeleteTest :
    BaseRepoDeleteTest<ExpenseHistoryRepository, ExpenseHistory>(testEntities = testEntities) {
    override fun initRepo(): ExpenseHistoryRepository = ExpenseHistoryRepositoryImpl(
        expenseHistoryMapper = expenseHistoryMapper,
        expenseHistoryDao = db.expenseHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}