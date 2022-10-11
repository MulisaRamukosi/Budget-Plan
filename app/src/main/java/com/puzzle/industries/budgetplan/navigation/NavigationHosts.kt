package com.puzzle.industries.budgetplan.navigation

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.puzzle.industries.budgetplan.navigation.graphs.appScreensGraph
import com.puzzle.industries.budgetplan.navigation.graphs.homeScreenGraph
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.ExpenseViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel

@Composable
fun AppScreensNavHost(modifier: Modifier = Modifier, windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    val graph = appScreensGraph(navController = navController, windowSizeClass = windowSizeClass)
    NavHost(
        modifier = modifier,
        navController = navController,
        graph = graph
    )
}

@Composable
fun HomeScreenNestedNavHost(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    navController: NavHostController
){
    val incomeViewModel: IncomeViewModel = hiltViewModel()
    val expenseViewModel: ExpenseViewModel = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        graph = homeScreenGraph(
            navController = navController,
            windowSizeClass = windowSizeClass,
            incomeViewModel = incomeViewModel,
            expenseViewModel = expenseViewModel
        )
    )
}
