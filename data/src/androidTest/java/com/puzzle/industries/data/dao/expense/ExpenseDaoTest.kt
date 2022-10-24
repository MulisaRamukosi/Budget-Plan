package com.puzzle.industries.data.dao.expense

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.dao.BaseDaoDeleteTest
import com.puzzle.industries.data.dao.BaseDaoInsertTest
import com.puzzle.industries.data.dao.BaseDaoReadTest
import com.puzzle.industries.data.dao.BaseDaoUpdateTest
import com.puzzle.industries.data.storage.database.dao.expense.ExpenseDao
import com.puzzle.industries.data.storage.database.entity.expense.ExpenseEntity
import com.puzzle.industries.domain.constants.FrequencyType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import java.util.*

private val testEntities = arrayOf(
    ExpenseEntity(
        expenseGroupId = UUID.randomUUID(),
        name = "name",
        amount = 123.0,
        frequencyType = FrequencyType.MONTHLY,
        description = "some desc",
        frequencyWhen = "asd"
    ),
    ExpenseEntity(
        expenseGroupId = UUID.randomUUID(),
        name = "other name",
        amount = 125.0,
        frequencyType = FrequencyType.YEARLY,
        description = "some desc",
        frequencyWhen = "sd"
    )
)

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        ExpenseDaoInsertTest::class,
        ExpenseDaoUpdateTest::class,
        ExpenseDaoDeleteTest::class,
        ExpenseDaoReadTest::class
    ]
)
internal class ExpenseDaoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseDaoInsertTest :
    BaseDaoInsertTest<ExpenseDao, ExpenseEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseDao = db.expenseDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseDaoUpdateTest :
    BaseDaoUpdateTest<ExpenseDao, ExpenseEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseDao = db.expenseDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseDaoDeleteTest :
    BaseDaoDeleteTest<ExpenseDao, ExpenseEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseDao = db.expenseDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseDaoReadTest :
    BaseDaoReadTest<ExpenseDao, ExpenseEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseDao = db.expenseDao()
}