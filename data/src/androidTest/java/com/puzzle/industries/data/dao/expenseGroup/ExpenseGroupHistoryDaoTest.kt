package com.puzzle.industries.data.dao.expenseGroup

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.dao.BaseDaoDeleteTest
import com.puzzle.industries.data.dao.BaseDaoInsertTest
import com.puzzle.industries.data.dao.BaseDaoReadTest
import com.puzzle.industries.data.database.dao.expenseGroup.ExpenseGroupHistoryDao
import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupHistoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = arrayOf(
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
internal class ExpenseGroupHistoryDaoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupHistoryDaoInsertTest :
    BaseDaoInsertTest<ExpenseGroupHistoryDao, ExpenseGroupHistoryEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseGroupHistoryDao = db.expenseGroupHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupHistoryDaoDeleteTest :
    BaseDaoDeleteTest<ExpenseGroupHistoryDao, ExpenseGroupHistoryEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseGroupHistoryDao = db.expenseGroupHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupHistoryDaoUpdateTest :
    BaseDaoReadTest<ExpenseGroupHistoryDao, ExpenseGroupHistoryEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseGroupHistoryDao = db.expenseGroupHistoryDao()
}