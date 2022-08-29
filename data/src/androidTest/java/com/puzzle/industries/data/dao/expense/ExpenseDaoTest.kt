package com.puzzle.industries.data.dao.expense

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.BaseDeleteTest
import com.puzzle.industries.data.BaseInsertTest
import com.puzzle.industries.data.BaseReadAllTest
import com.puzzle.industries.data.BaseUpdateTest
import com.puzzle.industries.data.database.dao.expense.ExpenseDao
import com.puzzle.industries.data.database.entity.expense.ExpenseEntity
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.runner.RunWith
import org.junit.runners.Suite
import java.util.*

private val testEntities = listOf(
    ExpenseEntity(
        expenseGroupId = UUID.randomUUID(),
        name = "name",
        amount = 123.0,
        frequency = "monthly"
    ),
    ExpenseEntity(
        expenseGroupId = UUID.randomUUID(),
        name = "other name",
        amount = 125.0,
        frequency = "yearly"
    )
)

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [

    ]
)
class ExpenseDaoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExpenseDaoInsertTest :
    BaseInsertTest<ExpenseDao, ExpenseEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseDao = db.expenseDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExpenseDaoUpdateTest :
    BaseUpdateTest<ExpenseDao, ExpenseEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseDao = db.expenseDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExpenseDaoDeleteTest :
    BaseDeleteTest<ExpenseDao, ExpenseEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseDao = db.expenseDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
class ExpenseDaoReadAllTest :
    BaseReadAllTest<ExpenseDao, ExpenseEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseDao = db.expenseDao()
}