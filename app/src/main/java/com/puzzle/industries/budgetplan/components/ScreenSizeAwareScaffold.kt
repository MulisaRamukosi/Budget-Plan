@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.WindowHeightSizeClass
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.WindowWidthSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavHostController
import androidx.navigation.compose.currentBackStackEntryAsState
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.BottomAppBarActionButton
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.bottomAppBar
import com.puzzle.industries.budgetplan.navigation.constants.Routes

@Composable
fun ScreenSizeAwareScaffold(
    modifier: Modifier = Modifier,
    windowSizeClass: WindowSizeClass,
    actions: List<BottomAppBarActionButton>,
    navController: NavHostController,
    content: @Composable (PaddingValues) -> Unit
) {
    val navBackStackEntry by navController.currentBackStackEntryAsState()
    val currentDestination = navBackStackEntry?.destination?.route
    val isInLandscape = windowSizeClass.widthSizeClass == WindowWidthSizeClass.Expanded
            || windowSizeClass.heightSizeClass == WindowHeightSizeClass.Compact

    Scaffold(
        modifier = modifier,
        bottomBar = conditionallyCreateBottomAppBar(
            generateBottomAppBar = !isInLandscape,
            currentDestination = currentDestination,
            actions = actions
        )
    ) {


        Row(modifier = Modifier.fillMaxSize()){
            if(isInLandscape) navigationRail(selectedRoute = currentDestination, actions = actions).invoke()
            content(it)
        }
    }
}

@Composable
private fun conditionallyCreateBottomAppBar(
    generateBottomAppBar: Boolean,
    currentDestination: String?,
    actions: List<BottomAppBarActionButton>
): @Composable () -> Unit {
    if (generateBottomAppBar) {
        return bottomAppBar(
            selectedRoute = currentDestination ?: Routes.Home.path,
            actions = actions
        )
    }
    return {}
}