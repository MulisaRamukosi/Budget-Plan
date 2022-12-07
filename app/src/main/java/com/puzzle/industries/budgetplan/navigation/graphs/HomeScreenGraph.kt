package com.puzzle.industries.budgetplan.navigation.graphs

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.screens.home.HomeScreen
import com.puzzle.industries.budgetplan.screens.home.SearchScreen
import com.puzzle.industries.budgetplan.screens.home.SettingsScreen
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.ExpenseViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.reminder.ReminderViewModel

@Composable
fun homeScreenGraph(
    mainNavController: NavHostController,
    homeScreenNavController: NavHostController,
    windowSizeClass: WindowSizeClass,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
    reminderViewModel: ReminderViewModel
): NavGraph {
    return homeScreenNavController.createGraph(startDestination = Routes.Home.path) {
        composable(route = Routes.Home.path) {
            HomeScreen(
                incomeViewModel = incomeViewModel,
                reminderViewModel = reminderViewModel,
                expenseViewModel = expenseViewModel
            )
        }

        budgetScreensGraph(
            navController = homeScreenNavController,
            windowSizeClass = windowSizeClass,
            incomeViewModel = incomeViewModel,
            expenseViewModel = expenseViewModel,
            reminderViewModel = reminderViewModel
        )

        composable(route = Routes.Stats.path) {
            SearchScreen()
        }

        composable(route = Routes.Settings.path) {
            SettingsScreen(mainNavController = mainNavController)
        }
    }
}