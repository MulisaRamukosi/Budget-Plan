package com.puzzle.industries.data.repo.income

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.mapper.income.IncomeHistoryMapper
import com.puzzle.industries.data.repo.BaseRepoDeleteTest
import com.puzzle.industries.data.repo.BaseRepoInsertTest
import com.puzzle.industries.data.repo.BaseRepoReadTest
import com.puzzle.industries.data.repository.income.IncomeHistoryRepositoryImpl
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.income.IncomeHistory
import com.puzzle.industries.domain.repository.income.IncomeHistoryRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite


private val testEntities = arrayOf(
    IncomeHistory(
        oldAmount = 12.0,
        newAmount = 34.0,
        oldFrequency = Frequency.DAILY,
        newFrequency = Frequency.MONTHLY,
        oldTitle = "old",
        newTitle = "new",
        reason = "some reason",
        action = Action.UPDATE
    ),
    IncomeHistory(
        oldAmount = 35000.0,
        newAmount = 60000.0,
        oldFrequency = Frequency.MONTHLY,
        newFrequency = Frequency.MONTHLY,
        oldTitle = "old title",
        newTitle = "new title",
        reason = "some reason for change",
        action = Action.UPDATE
    ),
)

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
        incomeHistoryMapper = IncomeHistoryMapper(),
        incomeHistoryDao = db.incomeHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeHistoryRepoReadTest :
    BaseRepoReadTest<IncomeHistoryRepository, IncomeHistory>(testEntities = testEntities) {
    override fun initRepo(): IncomeHistoryRepository = IncomeHistoryRepositoryImpl(
        incomeHistoryMapper = IncomeHistoryMapper(),
        incomeHistoryDao = db.incomeHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeHistoryRepoDeleteTest :
    BaseRepoDeleteTest<IncomeHistoryRepository, IncomeHistory>(testEntities = testEntities) {
    override fun initRepo(): IncomeHistoryRepository = IncomeHistoryRepositoryImpl(
        incomeHistoryMapper = IncomeHistoryMapper(),
        incomeHistoryDao = db.incomeHistoryDao(),
        responseMessageFactory = responseMessageFactory
    )
}
