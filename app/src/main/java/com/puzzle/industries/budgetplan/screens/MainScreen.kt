@file:OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3Api::class
)

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
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.BottomAppBarActionButton
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.bottomAppBar
import com.puzzle.industries.budgetplan.navigation.MainScreenNestedNavHost
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
fun MainScreen(navController: NavHostController = rememberNavController()) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        bottomBar = bottomAppBar(
            selectedRoute = currentDestination ?: Routes.Home.path,
            actions = bottomAppBarActions(navController = navController)
        )
    ) {

        MainScreenNestedNavHost(navController = navController, modifier = Modifier.padding(it))
    }
}

@Composable
private fun Content(
    navController: NavHostController
) {

}

@Composable
private fun bottomAppBarActions(navController: NavHostController): List<BottomAppBarActionButton> {
    return listOf(
        BottomAppBarActionButton(
            imageVector = Icons.Rounded.Home,
            label = stringResource(id = R.string.home),
            description = stringResource(id = R.string.desc_home),
            destinationRoute = Routes.Home.path,
            onActionClick = {
                bottomAppBarOnClick(
                    navController = navController,
                    destinationRoute = Routes.Home.path
                )
            }
        ),
        BottomAppBarActionButton(
            imageVector = Icons.Rounded.AccountBalanceWallet,
            label = stringResource(id = R.string.budget),
            description = stringResource(id = R.string.desc_budget),
            destinationRoute = Routes.Budget.path,
            onActionClick = {
                bottomAppBarOnClick(
                    navController = navController,
                    destinationRoute = Routes.Budget.path
                )
            }
        ),
        BottomAppBarActionButton(
            imageVector = Icons.Rounded.QueryStats,
            label = stringResource(id = R.string.search),
            description = stringResource(id = R.string.desc_search),
            destinationRoute = Routes.Search.path,
            onActionClick = {
                bottomAppBarOnClick(
                    navController = navController,
                    destinationRoute = Routes.Search.path
                )
            }
        )
    )
}

private fun bottomAppBarOnClick(navController: NavHostController, destinationRoute: String) {
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

@Composable
@Preview(showBackground = true)
private fun MainScreenPreview() {
    BudgetPlanTheme(dynamicColor = false) {
        MainScreen()
    }
}