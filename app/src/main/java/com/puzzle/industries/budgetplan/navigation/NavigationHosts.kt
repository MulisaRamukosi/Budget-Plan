package com.puzzle.industries.budgetplan.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.puzzle.industries.budgetplan.navigation.graphs.appScreensGraph
import com.puzzle.industries.budgetplan.navigation.graphs.mainScreenGraph

@Composable
@ExperimentalPagerApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
fun AppScreensNavHost(modifier: Modifier = Modifier) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        graph = appScreensGraph(navController = navController)
    )
}

@Composable
@ExperimentalPagerApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
fun MainScreenNestedNavHost(navController: NavHostController, modifier: Modifier = Modifier){
    NavHost(
        modifier = modifier,
        navController = navController,
        graph = mainScreenGraph(navController = navController)
    )
}
