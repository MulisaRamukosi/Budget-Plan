package com.puzzle.industries.data.repo.income

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.mapper.income.IncomeHistoryMapper
import com.puzzle.industries.data.mapper.income.IncomeMapper
import com.puzzle.industries.data.repo.BaseRepoDeleteTest
import com.puzzle.industries.data.repo.BaseRepoInsertTest
import com.puzzle.industries.data.repo.BaseRepoReadTest
import com.puzzle.industries.data.repository.income.IncomeHistoryRepositoryImpl
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.models.income.IncomeHistory
import com.puzzle.industries.domain.repository.income.IncomeHistoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite


private val testEntities = arrayOf(
    IncomeHistory(
        income = Income(
            frequency = Frequency.MONTHLY,
            amount = 1200.0,
            title = "income",
            description = "description"
        ),
        reason = "some reason",
        action = Action.UPDATE
    ),
    IncomeHistory(
        income = Income(
            frequency = Frequency.DAILY,
            amount = 120.0,
            title = "income",
            description = "description"
        ),
        reason = "some reason for change",
        action = Action.UPDATE
    ),
)

private val incomeHistoryMapper: IncomeHistoryMapper =
    IncomeHistoryMapper(incomeMapper = IncomeMapper())

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        IncomeHistoryRepoInsertTest::class,
        IncomeHistoryRepoReadTest::class,
        IncomeHistoryRepoDeleteTest::class,
    ]
)
internal class IncomeHistoryRepoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeHistoryRepoInsertTest :
    BaseRepoInsertTest<IncomeHistoryRepository, IncomeHistory>(testEntities = testEntities) {
    override fun initRepo(): IncomeHistoryRepository = IncomeHistoryRepositoryImpl(
        incomeHistoryMapper = incomeHistoryMapper,
        incomeHistoryDao = db.incomeHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeHistoryRepoReadTest :
    BaseRepoReadTest<IncomeHistoryRepository, IncomeHistory>(testEntities = testEntities) {
    override fun initRepo(): IncomeHistoryRepository = IncomeHistoryRepositoryImpl(
        incomeHistoryMapper = incomeHistoryMapper,
        incomeHistoryDao = db.incomeHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeHistoryRepoDeleteTest :
    BaseRepoDeleteTest<IncomeHistoryRepository, IncomeHistory>(testEntities = testEntities) {
    override fun initRepo(): IncomeHistoryRepository = IncomeHistoryRepositoryImpl(
        incomeHistoryMapper = incomeHistoryMapper,
        incomeHistoryDao = db.incomeHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}
