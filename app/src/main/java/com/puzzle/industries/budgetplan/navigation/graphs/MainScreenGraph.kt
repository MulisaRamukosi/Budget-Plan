package com.puzzle.industries.budgetplan.navigation.graphs

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.navigation.NavGraph
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.createGraph
import com.google.accompanist.pager.ExperimentalPagerApi
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.screens.main.HomeScreen
import com.puzzle.industries.budgetplan.screens.main.SearchScreen
import com.puzzle.industries.budgetplan.screens.budget.BudgetScreen

@Composable
@ExperimentalMaterial3Api
@ExperimentalPagerApi
fun mainScreenGraph(navController: NavHostController) : NavGraph {
    return navController.createGraph(startDestination = Routes.Home.path){
        composable(route = Routes.Home.path){
            HomeScreen()
        }

        composable(route = Routes.Budget.path){
            BudgetScreen()
        }

        composable(route = Routes.Search.path){
            SearchScreen()
        }
    }
}