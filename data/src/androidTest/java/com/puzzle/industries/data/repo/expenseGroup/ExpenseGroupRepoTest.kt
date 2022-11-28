package com.puzzle.industries.data.repo.expenseGroup

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.mapper.expense.ExpenseMapper
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupMapper
import com.puzzle.industries.data.repo.BaseRepoDeleteTest
import com.puzzle.industries.data.repo.BaseRepoInsertTest
import com.puzzle.industries.data.repo.BaseRepoTest
import com.puzzle.industries.data.repo.BaseRepoUpdateTest
import com.puzzle.industries.data.repository.expense.ExpenseRepositoryImpl
import com.puzzle.industries.data.repository.expenseGroup.ExpenseGroupRepositoryImpl
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Suite

private val testEntities = arrayOf(
    ExpenseGroup(
        name = "group 1",
        description = "",
        colorId = "color1"
    ),
    ExpenseGroup(
        name = "group 2",
        description = "",
        colorId = "color1"
    )
)

private val expenseGroupMapper: ExpenseGroupMapper =
    ExpenseGroupMapper(expenseMapper = ExpenseMapper())

@RunWith(Suite::class)
@ExperimentalCoroutinesApi
@Suite.SuiteClasses(
    value = [
        ExpenseGroupInsertTest::class,
        ExpenseGroupUpdateTest::class,
        ExpenseGroupDeleteTest::class,
        ExpenseGroupReadTest::class
    ]
)
internal class ExpenseGroupRepoTest

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupInsertTest
    : BaseRepoInsertTest<ExpenseGroupRepository, ExpenseGroup>(testEntities = testEntities) {
    override fun initRepo(): ExpenseGroupRepository = ExpenseGroupRepositoryImpl(
        expenseGroupMapper = expenseGroupMapper,
        expenseGroupDao = db.expenseGroupDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupUpdateTest :
    BaseRepoUpdateTest<ExpenseGroupRepository, ExpenseGroup>(testEntities = testEntities) {
    override fun initRepo(): ExpenseGroupRepository = ExpenseGroupRepositoryImpl(
        expenseGroupMapper = expenseGroupMapper,
        expenseGroupDao = db.expenseGroupDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupDeleteTest :
    BaseRepoDeleteTest<ExpenseGroupRepository, ExpenseGroup>(testEntities = testEntities) {
    override fun initRepo(): ExpenseGroupRepository = ExpenseGroupRepositoryImpl(
        expenseGroupMapper = expenseGroupMapper,
        expenseGroupDao = db.expenseGroupDao(),
        responseMessageFactory = responseMessageFactory
    )
}

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal class ExpenseGroupReadTest : BaseRepoTest<ExpenseGroupRepository>() {

    override fun initRepo(): ExpenseGroupRepository = ExpenseGroupRepositoryImpl(
        expenseGroupMapper = expenseGroupMapper,
        expenseGroupDao = db.expenseGroupDao(),
        responseMessageFactory = responseMessageFactory
    )

    @Test
    fun readExpenseGroup_DbHasNoExpenseGroups_ReturnsFlowWithEmpty() = runTest {
        val result = repo.read()

        assertTrue(result.response.first().isEmpty())
    }

    @Test
    fun readExpenseGroup_DbHasExpenseGroupsButNoExpensesLinked_ReturnsFlowOfExpenseGroups() =
        runTest {
            repo.insert(*testEntities)

            val result = repo.read()

            assertEquals(testEntities.size, result.response.first().size)
        }

    @Test
    fun readExpenseGroup_DbHasExpenseGroupsWithExpenses_ReturnsFlowOfExpenseGroupsWithExpenses() =
        runTest {
            val expenseRepo = ExpenseRepositoryImpl(
                expenseMapper = ExpenseMapper(),
                expenseDao = db.expenseDao(),
                responseMessageFactory = responseMessageFactory
            )

            val testExpenses = arrayOf(
                Expense(
                    expenseGroupId = testEntities[0].id,
                    name = "test1",
                    amount = 12.0,
                    description = "",
                    frequencyType = FrequencyType.DAILY,
                    frequencyWhen = ""
                ),
                Expense(
                    expenseGroupId = testEntities[0].id,
                    name = "test2",
                    amount = 13.4,
                    description = "",
                    frequencyType = FrequencyType.MONTHLY,
                    frequencyWhen = "1"
                )
            )

            repo.insert(*testEntities)
            expenseRepo.insert(*testExpenses)

            val result = repo.read()
            val expenseGroupsWithExpenses = result.response.first()

            assertEquals(testEntities.size, expenseGroupsWithExpenses.size)
            assertEquals(testExpenses.size, expenseGroupsWithExpenses[0].expenses.size)
            assertTrue(expenseGroupsWithExpenses[1].expenses.isEmpty())
        }
}