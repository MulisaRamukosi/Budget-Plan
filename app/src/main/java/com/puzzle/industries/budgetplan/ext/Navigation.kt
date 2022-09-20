package com.puzzle.industries.budgetplan.ext

import androidx.compose.runtime.Composable
import androidx.compose.runtime.livedata.observeAsState
import androidx.navigation.NavController

@Composable
fun <T> NavController.GetOnceResult(keyResult: String, onResult: (T) -> Unit){
    val valueScreenResult =  currentBackStackEntry
        ?.savedStateHandle
        ?.getLiveData<T>(keyResult)?.observeAsState()

    valueScreenResult?.value?.let {
        onResult(it)

        currentBackStackEntry
            ?.savedStateHandle
            ?.remove<T>(keyResult)
    }
}

@Composable
fun <T> NavController.SetResult(key: String, value: T){
    previousBackStackEntry?.savedStateHandle?.set(key, value)
}

fun NavController.navigateAndClearStack(route: String) {
    popBackStack(graph.startDestinationId, true)
    graph.setStartDestination(startDestRoute = route)
    navigate(route = route)
}