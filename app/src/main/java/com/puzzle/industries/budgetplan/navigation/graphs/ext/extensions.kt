package com.puzzle.industries.budgetplan.navigation.graphs.ext

import androidx.navigation.NavHostController

fun NavHostController.navigateAndClearStack(route: String) {
    popBackStack(graph.startDestinationId, true)
    graph.setStartDestination(startDestRoute = route)
    navigate(route = route)
}