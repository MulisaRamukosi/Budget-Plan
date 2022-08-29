package com.puzzle.industries.data.dao.income

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.BaseDeleteTest
import com.puzzle.industries.data.BaseInsertTest
import com.puzzle.industries.data.BaseReadAllTest
import com.puzzle.industries.data.BaseUpdateTest
import com.puzzle.industries.data.database.dao.income.IncomeDao
import com.puzzle.industries.data.database.entity.income.IncomeEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = listOf(
    IncomeEntity(
        frequency = "monthly",
        amount = 1200.0,
        title = "income",
        description = "description"
    ),
    IncomeEntity(
        frequency = "monthly",
        amount = 1200.0,
        title = "income",
        description = "description"
    )
)


@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        IncomeDaoInsertTest::class,
        IncomeDaoUpdateTest::class,
        IncomeDaoDeleteTest::class,
        IncomeDaoReadAllTest::class
    ]
)
class IncomeDaoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class IncomeDaoInsertTest : BaseInsertTest<IncomeDao, IncomeEntity>(testEntities = testEntities) {
    override fun initDao(): IncomeDao = db.incomeDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class IncomeDaoUpdateTest : BaseUpdateTest<IncomeDao, IncomeEntity>(testEntities = testEntities) {
    override fun initDao(): IncomeDao = db.incomeDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class IncomeDaoDeleteTest : BaseDeleteTest<IncomeDao, IncomeEntity>(testEntities = testEntities) {
    override fun initDao(): IncomeDao = db.incomeDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class IncomeDaoReadAllTest : BaseReadAllTest<IncomeDao, IncomeEntity>(testEntities = testEntities) {
    override fun initDao(): IncomeDao = db.incomeDao()
}