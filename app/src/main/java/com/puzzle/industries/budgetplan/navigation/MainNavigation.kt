package com.puzzle.industries.budgetplan.navigation

import androidx.compose.runtime.Composable
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import com.puzzle.industries.budgetplan.navigation.constants.MainScreens
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.screens.MainScreen
import com.puzzle.industries.budgetplan.screens.SplashScreen

@Composable
fun MainNavigation(){
    val navController = rememberNavController()

    NavHost(
        navController = navController,
        startDestination = MainScreens.Splash.name
    ){

        composable(route = Routes.Splash.path){
            SplashScreen(navController = navController)
        }

        composable(route = Routes.Main.path){
            MainScreen(navController = navController)
        }

    }
}