package com.puzzle.industries.data.dao.expenseGroup

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.BaseDeleteTest
import com.puzzle.industries.data.BaseInsertTest
import com.puzzle.industries.data.BaseReadTest
import com.puzzle.industries.data.database.dao.expenseGroup.ExpenseGroupHistoryDao
import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupHistoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = listOf(
    ExpenseGroupHistoryEntity(
        oldName = "old",
        newName = "new",
        action = "post",
        reason = "some reason"
    ),
    ExpenseGroupHistoryEntity(
        oldName = "old",
        newName = "new",
        action = "post",
        reason = "some reason"
    ),
)

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        ExpenseGroupHistoryDaoInsertTest::class,
        ExpenseGroupHistoryDaoDeleteTest::class,
        ExpenseGroupHistoryDaoUpdateTest::class
    ]
)
class ExpenseGroupHistoryDaoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExpenseGroupHistoryDaoInsertTest :
    BaseInsertTest<ExpenseGroupHistoryDao, ExpenseGroupHistoryEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseGroupHistoryDao = db.expenseGroupHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExpenseGroupHistoryDaoDeleteTest :
    BaseDeleteTest<ExpenseGroupHistoryDao, ExpenseGroupHistoryEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseGroupHistoryDao = db.expenseGroupHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExpenseGroupHistoryDaoUpdateTest :
    BaseReadTest<ExpenseGroupHistoryDao, ExpenseGroupHistoryEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseGroupHistoryDao = db.expenseGroupHistoryDao()
}