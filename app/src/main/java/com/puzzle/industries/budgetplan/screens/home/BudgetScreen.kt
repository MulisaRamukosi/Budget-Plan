@file:OptIn(
    ExperimentalPagerApi::class
)

package com.puzzle.industries.budgetplan.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.runtime.*
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
import com.puzzle.industries.budgetplan.screens.budget.ExpenseScreen
import com.puzzle.industries.budgetplan.screens.budget.IncomeScreen
import com.puzzle.industries.budgetplan.screens.budget.ReminderScreen
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.viewModels.budget.IncomeViewModel
import kotlinx.coroutines.launch
import java.util.*

@Composable
fun BudgetScreen(
    incomeViewModel: IncomeViewModel,
    navController: NavController,

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
                0 -> IncomeScreen(
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
                1 -> ExpenseScreen()
                2 -> ReminderScreen()
            }
        }
    }

}

private fun navigateToAddEditIncomeScreen(navController: NavController, incomeId: String) {
    navController.navigate(
        route = Routes.AddEditIncome.addParam(
            key = RouteParamKey.ID,
            value = incomeId
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
        BudgetScreen(viewModel(), rememberNavController())
    }
}

