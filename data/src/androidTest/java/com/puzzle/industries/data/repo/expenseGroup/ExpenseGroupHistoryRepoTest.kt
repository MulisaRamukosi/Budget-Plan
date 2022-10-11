package com.puzzle.industries.data.repo.expenseGroup

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupHistoryMapper
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupMapper
import com.puzzle.industries.data.repo.BaseRepoDeleteTest
import com.puzzle.industries.data.repo.BaseRepoInsertTest
import com.puzzle.industries.data.repo.BaseRepoReadTest
import com.puzzle.industries.data.repository.expenseGroup.ExpenseGroupHistoryRepositoryImpl
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupHistory
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupHistoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = arrayOf(
    ExpenseGroupHistory(
        expenseGroup = ExpenseGroup(
            name = "group 1",
            description = "some desc",
            colorId = ""
        ),
        action = Action.CREATE,
        reason = "some reason"
    ),
    ExpenseGroupHistory(
        expenseGroup = ExpenseGroup(
            name = "group 2",
            description = "some desc",
            colorId = "color1"
        ),
        action = Action.CREATE,
        reason = "some reason"
    )
)

private val expenseGroupHistoryMapper =
    ExpenseGroupHistoryMapper(expenseGroupMapper = ExpenseGroupMapper())

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        ExpenseGroupHistoryInsertTest::class,
        ExpenseGroupHistoryReadTest::class,
        ExpenseGroupHistoryDeleteTest::class
    ]
)
internal class ExpenseGroupHistoryRepoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupHistoryInsertTest :
    BaseRepoInsertTest<ExpenseGroupHistoryRepository, ExpenseGroupHistory>(testEntities = testEntities) {
    override fun initRepo(): ExpenseGroupHistoryRepository = ExpenseGroupHistoryRepositoryImpl(
        expenseGroupHistoryMapper = expenseGroupHistoryMapper,
        expenseGroupHistoryDao = db.expenseGroupHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupHistoryReadTest :
    BaseRepoReadTest<ExpenseGroupHistoryRepository, ExpenseGroupHistory>(testEntities = testEntities) {
    override fun initRepo(): ExpenseGroupHistoryRepository = ExpenseGroupHistoryRepositoryImpl(
        expenseGroupHistoryMapper = expenseGroupHistoryMapper,
        expenseGroupHistoryDao = db.expenseGroupHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupHistoryDeleteTest :
    BaseRepoDeleteTest<ExpenseGroupHistoryRepository, ExpenseGroupHistory>(testEntities = testEntities) {
    override fun initRepo(): ExpenseGroupHistoryRepository = ExpenseGroupHistoryRepositoryImpl(
        expenseGroupHistoryMapper = expenseGroupHistoryMapper,
        expenseGroupHistoryDao = db.expenseGroupHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}