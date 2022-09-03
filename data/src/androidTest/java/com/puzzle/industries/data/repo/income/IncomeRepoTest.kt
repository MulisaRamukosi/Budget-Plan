package com.puzzle.industries.data.repo.income

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.mapper.income.IncomeMapper
import com.puzzle.industries.data.repo.BaseRepoDeleteTest
import com.puzzle.industries.data.repo.BaseRepoInsertTest
import com.puzzle.industries.data.repo.BaseRepoReadTest
import com.puzzle.industries.data.repo.BaseRepoUpdateTest
import com.puzzle.industries.data.repository.income.IncomeRepositoryImpl
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.repository.income.IncomeRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = arrayOf(
    Income(
        frequency = Frequency.MONTHLY,
        amount = 1200.0,
        title = "income",
        description = "description"
    ),
    Income(
        frequency = Frequency.DAILY,
        amount = 120.0,
        title = "income",
        description = "description"
    )
)

private val incomeMapper: IncomeMapper = IncomeMapper()

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        IncomeRepoInsertTest::class,
        IncomeRepoReadTest::class,
        IncomeRepoDeleteTest::class,
        IncomeRepoUpdateTest::class
    ]
)
internal class IncomeRepoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeRepoInsertTest :
    BaseRepoInsertTest<IncomeRepository, Income>(testEntities = testEntities) {
    override fun initRepo(): IncomeRepository = IncomeRepositoryImpl(
        incomeMapper = incomeMapper,
        incomeDao = db.incomeDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeRepoReadTest :
    BaseRepoReadTest<IncomeRepository, Income>(testEntities = testEntities) {
    override fun initRepo(): IncomeRepository = IncomeRepositoryImpl(
        incomeMapper = incomeMapper,
        incomeDao = db.incomeDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeRepoDeleteTest :
    BaseRepoDeleteTest<IncomeRepository, Income>(testEntities = testEntities) {
    override fun initRepo(): IncomeRepository = IncomeRepositoryImpl(
        incomeMapper = incomeMapper,
        incomeDao = db.incomeDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeRepoUpdateTest :
    BaseRepoUpdateTest<IncomeRepository, Income>(testEntities = testEntities) {
    override fun initRepo(): IncomeRepository = IncomeRepositoryImpl(
        incomeMapper = incomeMapper,
        incomeDao = db.incomeDao(),
        responseMessageFactory = responseMessageFactory
    )
}