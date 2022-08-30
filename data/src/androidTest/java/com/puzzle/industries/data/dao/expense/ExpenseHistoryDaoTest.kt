package com.puzzle.industries.data.dao.expense

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.BaseDeleteTest
import com.puzzle.industries.data.BaseInsertTest
import com.puzzle.industries.data.BaseReadTest
import com.puzzle.industries.data.BaseUpdateTest
import com.puzzle.industries.data.database.dao.expense.ExpenseHistoryDao
import com.puzzle.industries.data.database.entity.expense.ExpenseHistoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import java.util.*

private val testEntities = listOf(
    ExpenseHistoryEntity(
        expenseGroupId = UUID.randomUUID(),
        oldName = "old",
        newName = "new",
        newAmount = 12.0,
        oldAmount = 14.5,
        oldFrequency = "monthly",
        newFrequency = "yearly",
        action = "update",
        reason = "sd"
    ),
    ExpenseHistoryEntity(
        expenseGroupId = UUID.randomUUID(),
        oldName = "old expense",
        newName = "new expense",
        newAmount = 12.0,
        oldAmount = 14.5,
        oldFrequency = "weekly",
        newFrequency = "monthly",
        action = "delete",
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
internal class ExpenseHistoryDaoInsertTest : BaseInsertTest<ExpenseHistoryDao, ExpenseHistoryEntity>(
    testEntities = testEntities) {
    override fun initDao(): ExpenseHistoryDao = db.expenseHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoUpdateTest : BaseUpdateTest<ExpenseHistoryDao, ExpenseHistoryEntity>(
    testEntities = testEntities) {
    override fun initDao(): ExpenseHistoryDao = db.expenseHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoDeleteTest : BaseDeleteTest<ExpenseHistoryDao, ExpenseHistoryEntity>(
    testEntities = testEntities) {
    override fun initDao(): ExpenseHistoryDao = db.expenseHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseHistoryDaoReadTest : BaseReadTest<ExpenseHistoryDao, ExpenseHistoryEntity>(
    testEntities = testEntities) {
    override fun initDao(): ExpenseHistoryDao = db.expenseHistoryDao()
}