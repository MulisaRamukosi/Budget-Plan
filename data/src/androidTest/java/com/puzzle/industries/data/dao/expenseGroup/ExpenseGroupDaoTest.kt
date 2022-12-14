package com.puzzle.industries.data.dao.expenseGroup

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.dao.BaseDaoTest
import com.puzzle.industries.data.dao.BaseDaoDeleteTest
import com.puzzle.industries.data.dao.BaseDaoInsertTest
import com.puzzle.industries.data.dao.BaseDaoUpdateTest
import com.puzzle.industries.data.storage.database.dao.expenseGroup.ExpenseGroupDao
import com.puzzle.industries.data.storage.database.entity.expense.ExpenseEntity
import com.puzzle.industries.data.storage.database.entity.expenseGroup.ExpenseGroupEntity
import com.puzzle.industries.domain.constants.FrequencyType
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertNotNull
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = arrayOf(
    ExpenseGroupEntity(name = "some name", description = "some desc", colorId = "1"),
    ExpenseGroupEntity(name = "some other name", description = "some desc", colorId = "2")
)

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        ExpenseGroupDaoInsertTest::class,
        ExpenseGroupDaoDeleteTest::class,
        ExpenseGroupDaoUpdateTest::class,
        ExpenseGroupDaoReadTest::class
    ]
)
internal class ExpenseGroupDaoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupDaoInsertTest :
    BaseDaoInsertTest<ExpenseGroupDao, ExpenseGroupEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseGroupDao = db.expenseGroupDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupDaoDeleteTest :
    BaseDaoDeleteTest<ExpenseGroupDao, ExpenseGroupEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseGroupDao = db.expenseGroupDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupDaoUpdateTest :
    BaseDaoUpdateTest<ExpenseGroupDao, ExpenseGroupEntity>(testEntities = testEntities) {
    override fun initDao(): ExpenseGroupDao = db.expenseGroupDao()
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupDaoReadTest : BaseDaoTest<ExpenseGroupDao>() {
    override fun initDao(): ExpenseGroupDao = db.expenseGroupDao()

    @Test
    fun readAllGroupsWithExpenses_DbEmpty_ReturnsEmptyList() = runTest {
        val expenseGroupsWithExpenses = dao.read().first()

        assertTrue(expenseGroupsWithExpenses.isEmpty())
    }

    @Test
    fun readAllGroupsWithExpenses_DbHasExpenseGroupButNoExpenses_ReturnsExpenseGroupsWithNoExpenses() =
        runTest {
            dao.insert(*testEntities)

            val expenseGroupsWithExpenses = dao.read().first()

            expenseGroupsWithExpenses.forEach {
                assertNotNull(it.expenseGroup)
                assertTrue(it.expenses.isEmpty())
            }
        }

    @Test
    fun readAllGroupsWithExpenses_DbHasExpenseGroupWithExpenses_ReturnsExpenseGroupsWithExpenses() =
        runTest {
            testEntities.forEach {
                dao.insert(it)
                db.expenseDao().insert(
                    ExpenseEntity(
                        expenseGroupId = it.id,
                        name = "test",
                        amount = 12.0,
                        frequencyType = FrequencyType.MONTHLY,
                        frequencyWhen = "",
                        description = "some desc"
                    )
                )
            }

            val expenseGroupsWithExpenses = dao.read().first()

            expenseGroupsWithExpenses.forEach {
                assertNotNull(it.expenseGroup)
                assertTrue(it.expenses.isNotEmpty())
            }
        }
}
