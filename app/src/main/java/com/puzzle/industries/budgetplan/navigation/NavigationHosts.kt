@file:OptIn(
    ExperimentalPagerApi::class,
    ExperimentalMaterial3Api::class,
    ExperimentalAnimationApi::class
)

package com.puzzle.industries.budgetplan.navigation

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.rememberNavController
import com.google.accompanist.pager.ExperimentalPagerApi
import com.puzzle.industries.budgetplan.navigation.graphs.appScreensGraph
import com.puzzle.industries.budgetplan.navigation.graphs.mainScreenGraph

@Composable
fun AppScreensNavHost(modifier: Modifier = Modifier, windowSizeClass: WindowSizeClass) {
    val navController = rememberNavController()
    NavHost(
        modifier = modifier,
        navController = navController,
        graph = appScreensGraph(navController = navController, windowSizeClass)
    )
}

@Composable
fun MainScreenNestedNavHost(
    modifier: Modifier = Modifier,
    navController: NavHostController
){
    val incomeViewModel: IncomeViewModel  = hiltViewModel()

    NavHost(
        modifier = modifier,
        navController = navController,
        graph = homeScreenGraph(
            navController = navController,
            incomeViewModel = incomeViewModel
        )
    )
}
