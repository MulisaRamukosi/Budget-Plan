@file:OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class
)

package com.puzzle.industries.budgetplan.screens

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.QueryStats
import androidx.compose.material.icons.rounded.Settings
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.DpSize
import androidx.compose.ui.unit.dp
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.NavHostController
import androidx.navigation.compose.rememberNavController
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.layout.ScreenSizeAwareScaffold
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.BottomAppBarActionButton
import com.puzzle.industries.budgetplan.navigation.HomeScreenNestedNavHost
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
fun MainScreen(
    homeScreenNavController: NavHostController = rememberNavController(),
    mainNavController: NavHostController,
    windowSizeClass: WindowSizeClass
) {
    ScreenSizeAwareScaffold(
        windowSizeClass = windowSizeClass,
        actions = actions(navController = homeScreenNavController),
        navController = homeScreenNavController
    ) { paddingValues ->

        HomeScreenNestedNavHost(
            modifier = Modifier.padding(paddingValues = paddingValues).fillMaxSize(),
            mainNavController = mainNavController,
            homeScreenNavController = homeScreenNavController,
            windowSizeClass = windowSizeClass
        )

    }
}

@Composable
private fun actions(navController: NavHostController): List<BottomAppBarActionButton> {
    return listOf(
        BottomAppBarActionButton(
            imageVector = Icons.Rounded.Home,
            label = stringResource(id = R.string.home),
            description = stringResource(id = R.string.desc_home),
            destinationRoute = Routes.Home.path,
            onActionClick = { route ->
                bottomAppBarOnClick(
                    navController = navController,
                    destinationRoute = route
                )
            }
        ),
        BottomAppBarActionButton(
            imageVector = Icons.Rounded.AccountBalanceWallet,
            label = stringResource(id = R.string.budget),
            description = stringResource(id = R.string.desc_budget),
            destinationRoute = Routes.Budget.path,
            onActionClick = { route ->
                bottomAppBarOnClick(
                    navController = navController,
                    destinationRoute = route
                )
            }
        ),
        BottomAppBarActionButton(
            imageVector = Icons.Rounded.QueryStats,
            label = stringResource(id = R.string.statistics),
            description = stringResource(id = R.string.desc_search),
            destinationRoute = Routes.Stats.path,
            onActionClick = { route ->
                bottomAppBarOnClick(
                    navController = navController,
                    destinationRoute = route
                )
            }
        ),
        BottomAppBarActionButton(
            imageVector = Icons.Rounded.Settings,
            label = stringResource(id = R.string.settings),
            description = stringResource(id = R.string.desc_settings),
            destinationRoute = Routes.Settings.path,
            onActionClick = { route ->
                bottomAppBarOnClick(
                    navController = navController,
                    destinationRoute = route
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
        MainScreen(
            mainNavController = rememberNavController(),
            windowSizeClass = WindowSizeClass.calculateFromSize(
                DpSize(
                    width = 500.dp,
                    height = 800.dp
                )
            )
        )
    }
}