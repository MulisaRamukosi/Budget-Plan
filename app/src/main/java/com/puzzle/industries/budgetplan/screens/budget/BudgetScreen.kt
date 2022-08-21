package com.puzzle.industries.budgetplan.screens.budget

import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Notifications
import androidx.compose.material.icons.rounded.Paid
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.*
import androidx.compose.material3.TabRowDefaults.tabIndicatorOffset
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.google.accompanist.pager.ExperimentalPagerApi
import com.google.accompanist.pager.HorizontalPager
import com.google.accompanist.pager.rememberPagerState
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import kotlinx.coroutines.launch

@Composable
@ExperimentalPagerApi
fun BudgetScreen() {
    val tabs = listOf(
        stringResource(id = R.string.income) to Icons.Rounded.Paid,
        stringResource(id = R.string.expenses) to Icons.Rounded.Payments,
        stringResource(id = R.string.reminder) to Icons.Rounded.Notifications
    )

    val pagerState = rememberPagerState(
        initialPage = 0
    )

    val coroutineScope = rememberCoroutineScope()

    Column {
        TabRow(selectedTabIndex = pagerState.currentPage) {
            tabs.forEachIndexed { index, tab ->
                Tab(
                    selected = pagerState.currentPage == index,
                    onClick = {
                        coroutineScope.launch {
                            pagerState.animateScrollToPage(page = index)
                        }
                    },
                    icon = { Icon(imageVector = tab.second, contentDescription = tab.first) },
                    unselectedContentColor = MaterialTheme.colorScheme.secondary,
                    text = {
                        Text(
                            text = tab.first,
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
    }

}

@Composable
@Preview(showBackground = true)
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@ExperimentalMaterial3WindowSizeClassApi
private fun ScreenPreview() {
    BudgetPlanTheme(dynamicColor = false) {
        BudgetScreen()
    }
}

