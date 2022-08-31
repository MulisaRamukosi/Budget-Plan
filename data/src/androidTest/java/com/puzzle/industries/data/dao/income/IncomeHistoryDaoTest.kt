package com.puzzle.industries.data.dao.income

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.dao.BaseDaoDeleteTest
import com.puzzle.industries.data.dao.BaseDaoInsertTest
import com.puzzle.industries.data.dao.BaseDaoReadTest
import com.puzzle.industries.data.database.dao.income.IncomeHistoryDao
import com.puzzle.industries.data.database.entity.income.IncomeHistoryEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = listOf(
    IncomeHistoryEntity(
        oldAmount = 12.0,
        newAmount = 13.0,
        oldFrequency = "monthly",
        newFrequency = "yearly",
        oldTitle = "oldTitle",
        newTitle = "newTitle",
        reason = "some reason",
        action = "update"
    ),
    IncomeHistoryEntity(
        oldAmount = 18.0,
        newAmount = 13.0,
        oldFrequency = "yearly",
        newFrequency = "weekly",
        oldTitle = "oldTitle",
        newTitle = "newTitle",
        reason = "some reason",
        action = "update"
    )
)

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        IncomeHistoryDaoInsertTest::class,
        IncomeHistoryDaoDeleteTest::class,
        IncomeHistoryDaoReadTest::class
    ]
)
internal class IncomeHistoryDaoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeHistoryDaoInsertTest : BaseDaoInsertTest<IncomeHistoryDao, IncomeHistoryEntity>(
    testEntities = testEntities
) {
    override fun initDao(): IncomeHistoryDao = db.incomeHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeHistoryDaoDeleteTest : BaseDaoDeleteTest<IncomeHistoryDao, IncomeHistoryEntity>(
    testEntities = testEntities
) {
    override fun initDao(): IncomeHistoryDao = db.incomeHistoryDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeHistoryDaoReadTest : BaseDaoReadTest<IncomeHistoryDao, IncomeHistoryEntity>(
    testEntities = testEntities
) {
    override fun initDao(): IncomeHistoryDao = db.incomeHistoryDao()
}
