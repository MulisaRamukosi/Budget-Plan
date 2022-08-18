package com.puzzle.industries.budgetplan.screens

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.AccountBalanceWallet
import androidx.compose.material.icons.rounded.Home
import androidx.compose.material.icons.rounded.QueryStats
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.navigation.NavHostController
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.appBar.ActionButton
import com.puzzle.industries.budgetplan.components.appBar.bottomAppBar.bottomAppBar
import com.puzzle.industries.budgetplan.components.appBar.topAppBar.topAppBar
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
fun MainScreen(navController: NavHostController) {

}

@Composable
@ExperimentalMaterial3Api
private fun Content(){

    Scaffold(
        modifier = Modifier.fillMaxSize(),
        topBar = topAppBar(),
        bottomBar = bottomAppBar(
            actions = listOf(
                ActionButton(
                    imageVector = Icons.Rounded.Home,
                    label = stringResource(id = R.string.home),
                    description = stringResource(id = R.string.desc_home),
                    onActionClick = {}
                ),
                ActionButton(
                    imageVector = Icons.Rounded.AccountBalanceWallet,
                    label = stringResource(id = R.string.budget),
                    description = stringResource(id = R.string.desc_budget),
                    onActionClick = {}
                ),
                ActionButton(
                    imageVector = Icons.Rounded.QueryStats,
                    label = stringResource(id = R.string.search),
                    description = stringResource(id = R.string.desc_search),
                    onActionClick = {}
                )
            )
        )
    ) {

    }
}

@Composable
@Preview(showBackground = true)
@ExperimentalMaterial3Api
@ExperimentalMaterial3WindowSizeClassApi
private fun ScreenPreview() {
    BudgetPlanTheme(dynamicColor = false) {
        Content()
    }
}