package com.puzzle.industries.data.dao.income

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.dao.BaseDaoDeleteTest
import com.puzzle.industries.data.dao.BaseDaoInsertTest
import com.puzzle.industries.data.dao.BaseDaoReadTest
import com.puzzle.industries.data.storage.database.dao.income.IncomeHistoryDao
import com.puzzle.industries.data.storage.database.entity.income.IncomeEntity
import com.puzzle.industries.data.storage.database.entity.income.IncomeHistoryEntity
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.FrequencyType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = arrayOf(
    IncomeHistoryEntity(
        income = IncomeEntity(
            frequencyType = FrequencyType.YEARLY,
            frequencyWhen = "",
            amount = 13.0,
            title = "some title",
            description = "some desc"
        ),
        reason = "some reason",
        action = Action.CREATE
    ),
    IncomeHistoryEntity(
        income = IncomeEntity(
            frequencyType = FrequencyType.MONTHLY,
            frequencyWhen = "",
            amount = 138.0,
            title = "other title",
            description = "some more desc"
        ),
        reason = "some reason",
        action = Action.UPDATE
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
