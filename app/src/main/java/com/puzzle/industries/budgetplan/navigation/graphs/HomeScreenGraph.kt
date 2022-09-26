package com.puzzle.industries.budgetplan.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.puzzle.industries.budgetplan.navigation.constants.RouteParamKey
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.screens.home.BudgetScreen
import com.puzzle.industries.budgetplan.screens.home.HomeScreen
import com.puzzle.industries.budgetplan.screens.home.SearchScreen
import com.puzzle.industries.budgetplan.screens.home.SettingsScreen
import com.puzzle.industries.budgetplan.viewModels.budget.IncomeViewModel
import java.util.*

@Composable
fun homeScreenGraph(
    navController: NavHostController,
    incomeViewModel: IncomeViewModel
): NavGraph {
    return navController.createGraph(startDestination = Routes.Home.path) {
        composable(route = Routes.Home.path) {
            HomeScreen(incomeViewModel = incomeViewModel)
        }

        budgetScreensGraph(navController = navController, incomeViewModel = incomeViewModel)
        

        composable(route = Routes.Search.path) {
            SearchScreen()
        }

        composable(route = Routes.Settings.path) {
            SettingsScreen()
        }
    }
}