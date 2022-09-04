package com.puzzle.industries.data.dao.expense

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.dao.BaseDaoDeleteTest
import com.puzzle.industries.data.dao.BaseDaoInsertTest
import com.puzzle.industries.data.dao.BaseDaoReadTest
import com.puzzle.industries.data.dao.BaseDaoUpdateTest
import com.puzzle.industries.data.database.dao.expense.ExpenseHistoryDao
import com.puzzle.industries.data.database.entity.expense.ExpenseEntity
import com.puzzle.industries.data.database.entity.expense.ExpenseHistoryEntity
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.Frequency
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import java.util.*

private val testEntities = arrayOf(
    ExpenseHistoryEntity(
        expense = ExpenseEntity(
            expenseGroupId = UUID.randomUUID(),
            name = "new",
            amount = 12.0,
            frequency = Frequency.MONTHLY
        ),
        action = Action.UPDATE,
        reason = "sd"
    ),
    ExpenseHistoryEntity(
        expense = ExpenseEntity(
            expenseGroupId = UUID.randomUUID(),
            name = "new",
            amount = 12.0,
            frequency = Frequency.MONTHLY
        ),
        action = Action.DELETE,
        reason = "sd"
    )
)

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        ExpenseHistoryDaoInsertTest::class,
        ExpenseHistoryDaoUpdateTest::class,
        ExpenseHistoryDaoDeleteTest::class,
        ExpenseHistoryDaoReadTest::class
    ]
)
internal class ExpenseHistoryDaoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoInsertTest : BaseDaoInsertTest<ExpenseHistoryDao, ExpenseHistoryEntity>(
    testEntities = testEntities) {
    override fun initDao(): ExpenseHistoryDao = db.expenseHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoUpdateTest : BaseDaoUpdateTest<ExpenseHistoryDao, ExpenseHistoryEntity>(
    testEntities = testEntities) {
    override fun initDao(): ExpenseHistoryDao = db.expenseHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoDeleteTest : BaseDaoDeleteTest<ExpenseHistoryDao, ExpenseHistoryEntity>(
    testEntities = testEntities) {
    override fun initDao(): ExpenseHistoryDao = db.expenseHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoReadTest : BaseDaoReadTest<ExpenseHistoryDao, ExpenseHistoryEntity>(
    testEntities = testEntities) {
    override fun initDao(): ExpenseHistoryDao = db.expenseHistoryDao()
}