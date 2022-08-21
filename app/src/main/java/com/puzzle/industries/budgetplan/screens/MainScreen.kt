package com.puzzle.industries.budgetplan.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.QueryStats
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Scaffold
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.BottomAppBarActionButton
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.bottomAppBar
import com.puzzle.industries.budgetplan.components.appBar.topAppBar.topAppBar
import com.puzzle.industries.budgetplan.navigation.MainScreenNestedNavHost
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
@ExperimentalPagerApi
@ExperimentalMaterial3Api
fun MainScreen() {
    val nestedNavController = rememberNavController()

    Content(
        navController = nestedNavController
    )
}

@Composable
@ExperimentalPagerApi
@ExperimentalMaterial3Api
private fun Content(
    navController: NavHostController
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    val bottomAppBarItemClick: (String) -> Unit = { destinationRoute ->
        run {
            navController.navigate(route = destinationRoute) {
                navController.graph.findStartDestination().let {
                    popUpTo(it.id) {
                        saveState = true
                    }
                }
                launchSingleTop = true
                restoreState = true
            }
        }
    }


    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = topAppBar(),
        bottomBar = bottomAppBar(
            selectedRoute = currentDestination ?: Routes.Home.path,
            actions = listOf(
                BottomAppBarActionButton(
                    imageVector = Icons.Rounded.Home,
                    label = stringResource(id = R.string.home),
                    description = stringResource(id = R.string.desc_home),
                    destinationRoute = Routes.Home.path,
                    onActionClick = { bottomAppBarItemClick(Routes.Home.path) }
                ),
                BottomAppBarActionButton(
                    imageVector = Icons.Rounded.AccountBalanceWallet,
                    label = stringResource(id = R.string.budget),
                    description = stringResource(id = R.string.desc_budget),
                    destinationRoute = Routes.Budget.path,
                    onActionClick = { bottomAppBarItemClick(Routes.Budget.path) }
                ),
                BottomAppBarActionButton(
                    imageVector = Icons.Rounded.QueryStats,
                    label = stringResource(id = R.string.search),
                    description = stringResource(id = R.string.desc_search),
                    destinationRoute = Routes.Search.path,
                    onActionClick = { bottomAppBarItemClick(Routes.Search.path) }
                )
            )
        )
    ) {
        MainScreenNestedNavHost(navController = navController, modifier = Modifier.padding(it))
    }
}


@Composable
@Preview(showBackground = true)
@ExperimentalMaterial3Api
@ExperimentalPagerApi
@ExperimentalMaterial3WindowSizeClassApi
private fun ScreenPreview() {
    BudgetPlanTheme(dynamicColor = false) {
        MainScreen()
    }
}