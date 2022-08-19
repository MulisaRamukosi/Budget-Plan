package com.puzzle.industries.budgetplan.navigation

import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.puzzle.industries.budgetplan.navigation.graphs.appScreensGraph
import com.puzzle.industries.budgetplan.navigation.graphs.mainScreenGraph

@Composable
@ExperimentalMaterial3Api
fun AppScreensNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        graph = appScreensGraph(navController = navController)
    )
}

@Composable
@ExperimentalMaterial3Api
fun mainScreenNestedNavHost(modifier: Modifier = Modifier) : NavHostController{
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        graph = mainScreenGraph(navController = navController)
    )

    return navController
}
