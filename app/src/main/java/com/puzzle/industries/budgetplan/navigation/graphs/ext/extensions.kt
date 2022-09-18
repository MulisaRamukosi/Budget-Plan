package com.puzzle.industries.budgetplan.navigation.graphs.ext

import androidx.navigation.NavHostController
import androidx.navigation.NavOptionsBuilder

fun NavOptionsBuilder.clearAllPreviousRoutes(navController: NavHostController) {
    navController.graph.startDestinationRoute?.let {
        popUpTo(route = it) {
            inclusive = true
        }
    }
}