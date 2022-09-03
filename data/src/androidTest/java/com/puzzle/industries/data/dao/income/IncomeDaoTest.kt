package com.puzzle.industries.data.dao.income

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.dao.BaseDaoDeleteTest
import com.puzzle.industries.data.dao.BaseDaoInsertTest
import com.puzzle.industries.data.dao.BaseDaoReadTest
import com.puzzle.industries.data.dao.BaseDaoUpdateTest
import com.puzzle.industries.data.database.dao.income.IncomeDao
import com.puzzle.industries.data.database.entity.income.IncomeEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = arrayOf(
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
        IncomeDaoReadTest::class
    ]
)
internal class IncomeDaoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeDaoInsertTest :
    BaseDaoInsertTest<IncomeDao, IncomeEntity>(testEntities = testEntities) {
    override fun initDao(): IncomeDao = db.incomeDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeDaoUpdateTest :
    BaseDaoUpdateTest<IncomeDao, IncomeEntity>(testEntities = testEntities) {
    override fun initDao(): IncomeDao = db.incomeDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeDaoDeleteTest :
    BaseDaoDeleteTest<IncomeDao, IncomeEntity>(testEntities = testEntities) {
    override fun initDao(): IncomeDao = db.incomeDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class IncomeDaoReadTest :
    BaseDaoReadTest<IncomeDao, IncomeEntity>(testEntities = testEntities) {
    override fun initDao(): IncomeDao = db.incomeDao()
}