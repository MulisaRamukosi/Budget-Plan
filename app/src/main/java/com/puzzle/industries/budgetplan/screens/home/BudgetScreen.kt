@file:OptIn(
    ExperimentalPagerApi::class,
)

package com.puzzle.industries.budgetplan.screens.home

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.Column
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.screens.budget.ExpenseScreen
import com.puzzle.industries.budgetplan.screens.budget.IncomeScreen
import com.puzzle.industries.budgetplan.screens.budget.ReminderScreen
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import kotlinx.coroutines.launch


@Composable
fun BudgetScreen() {
    val tabs = listOf(
        stringResource(id = R.string.income),
        stringResource(id = R.string.expenses),
        stringResource(id = R.string.reminder)
    )

    val pagerState = rememberPagerState(
        initialPage = 0
    )

    val coroutineScope = rememberCoroutineScope()

    /*Column {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, title ->
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
        HorizontalPager(
            count = tabs.size,
            state = pagerState,
            modifier = Modifier.weight(1f)
        ) { currentPage ->
            when(currentPage) {
                0 -> IncomeScreen()
                1 -> ExpenseScreen()
                2 -> ReminderScreen()
            }
        }
    }*/

}

@Composable
@Preview(showBackground = true)
private fun BudgetScreenPreview() {
    BudgetPlanTheme(dynamicColor = false) {
        BudgetScreen()
    }
}

