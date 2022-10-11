@file:OptIn(
    ExperimentalPagerApi::class
)

package com.puzzle.industries.budgetplan.screens.home

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Tab
import androidx.compose.material3.TabRow
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.rememberCoroutineScope
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavController
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.PagerState
import com.google.accompanist.pager.rememberPagerState
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.navigation.constants.RouteParamKey
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.screens.budget.ReminderScreen
import com.puzzle.industries.budgetplan.screens.budget.expense.ExpenseScreen
import com.puzzle.industries.budgetplan.screens.budget.income.IncomeScreen
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.ExpenseViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun BudgetScreen(
    navController: NavController,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel
) {
    val tabs = listOf(
        stringResource(id = R.string.income),
        stringResource(id = R.string.expenses),
        stringResource(id = R.string.reminder)
    )

    val pagerState = rememberPagerState(initialPage = 0)

    Column {
        TabLayout(pagerState = pagerState, tabTitles = tabs)

        HorizontalPager(
            count = tabs.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { currentPage ->
            when (currentPage) {
                0 -> IncomeScreenTab(incomeViewModel, navController)
                1 -> ExpenseScreenTab(expenseViewModel, navController)
                2 -> ReminderScreen()
            }
        }
    }

}

@Composable
private fun ExpenseScreenTab(
    expenseViewModel: ExpenseViewModel,
    navController: NavController
) {
    ExpenseScreen(
        expenseViewModel = expenseViewModel,
        onEditExpenseGroup = { expenseGroup ->
            navigateToAddEditExpenseGroupScreen(
                navController = navController,
                expenseGroupId = expenseGroup.id.toString()
            )
        },
        onEditExpense = { expense ->
            navigateToAddEditExpenseScreen(
                navController = navController,
                expenseGroupId = expense.expenseGroupId.toString(),
                expenseId = expense.id.toString()
            )
        },
        onAddExpenseGroup = {
            navigateToAddEditExpenseGroupScreen(
                navController = navController,
                expenseGroupId = UUID.randomUUID().toString()
            )
        },
        onAddExpense = { expenseGroup ->
            navigateToAddEditExpenseScreen(
                navController = navController,
                expenseGroupId = expenseGroup.id.toString(),
                expenseId = UUID.randomUUID().toString()
            )
        }
    )
}

@Composable
private fun IncomeScreenTab(
    incomeViewModel: IncomeViewModel,
    navController: NavController
) {
    IncomeScreen(
        incomeViewModel = incomeViewModel,
        onEditItemClick = { income ->
            navigateToAddEditIncomeScreen(
                navController = navController,
                incomeId = income.id.toString()
            )
        },
        onAddIncomeClick = {
            navigateToAddEditIncomeScreen(
                navController = navController,
                incomeId = UUID.randomUUID().toString()
            )
        }
    )
}

private fun navigateToAddEditExpenseScreen(
    navController: NavController,
    expenseGroupId: String,
    expenseId: String
) {
    navController.navigate(
        route = Routes.AddEditExpense
            .addParam(
                key = RouteParamKey.EXPENSE_GROUP_ID,
                value = expenseGroupId
            )
            .addParam(key = RouteParamKey.ID, value = expenseId)
            .path
    )
}

private fun navigateToAddEditIncomeScreen(navController: NavController, incomeId: String) {
    navController.navigate(
        route = Routes.AddEditIncome.addParam(
            key = RouteParamKey.ID,
            value = incomeId
        ).path
    )
}

private fun navigateToAddEditExpenseGroupScreen(
    navController: NavController,
    expenseGroupId: String
) {
    navController.navigate(
        route = Routes.AddEditExpenseGroup.addParam(
            key = RouteParamKey.ID,
            value = expenseGroupId
        ).path
    )
}

@Composable
private fun TabLayout(
    pagerState: PagerState,
    tabTitles: List<String>
) {
    val coroutineScope = rememberCoroutineScope()

    TabRow(selectedTabIndex = pagerState.currentPage) {
        tabTitles.forEachIndexed { index, title ->
            Tab(
                selected = pagerState.currentPage == index,
                onClick = {
                    coroutineScope.launch {
                        pagerState.animateScrollToPage(page = index)
                    }
                },
                unselectedContentColor = MaterialTheme.colorScheme.secondary,
                text = {
                    Text(
                        text = title,
                        maxLines = 2,
                        overflow = TextOverflow.Ellipsis
                    )
                }
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
private fun BudgetScreenPreview() {
    BudgetPlanTheme(dynamicColor = false) {
        BudgetScreen(rememberNavController(), viewModel(), viewModel())
    }
}

