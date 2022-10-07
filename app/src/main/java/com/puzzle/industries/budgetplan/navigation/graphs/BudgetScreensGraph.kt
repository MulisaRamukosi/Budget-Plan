package com.puzzle.industries.budgetplan.navigation.graphs

import android.app.Activity
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.MainActivity
import com.puzzle.industries.budgetplan.ext.isInTabletLandscapeView
import com.puzzle.industries.budgetplan.navigation.constants.RouteParamKey
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.screens.budget.income.AddEditIncomeScreen
import com.puzzle.industries.budgetplan.screens.home.BudgetScreen
import com.puzzle.industries.budgetplan.viewModels.budget.income.AddEditIncomeViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import com.puzzle.industries.domain.models.income.Income
import dagger.hilt.android.EntryPointAccessors
import java.util.*

fun NavGraphBuilder.budgetScreensGraph(
    navController: NavController,
    windowSizeClass: WindowSizeClass,
    incomeViewModel: IncomeViewModel
) {
    navigation(startDestination = Routes.Budget.path, route = Routes.BudgetRoute.path) {
        budgetScreen(navController = navController, incomeViewModel = incomeViewModel)
        addEditIncomeScreen(
            navController = navController,
            windowSizeClass = windowSizeClass,
            incomeViewModel = incomeViewModel
        )
    }
}

private fun NavGraphBuilder.budgetScreen(
    navController: NavController,
    incomeViewModel: IncomeViewModel
) {
    composable(route = Routes.Budget.path) {
        BudgetScreen(
            incomeViewModel = incomeViewModel,
            navController = navController
        )
    }
}

private fun NavGraphBuilder.addEditIncomeScreen(
    navController: NavController,
    windowSizeClass: WindowSizeClass,
    incomeViewModel: IncomeViewModel
) {
    composable(
        route = Routes.AddEditIncome.path,
        arguments = listOf(
            navArgument(name = RouteParamKey.ID) { type = NavType.StringType }
        )
    ) { navBackStackEntry ->

        AddEditIncomeScreen(
            incomeViewModel = incomeViewModel,
            addEditIncomeViewModel = addEditIncomeViewModel(
                owner = navBackStackEntry,
                incomeViewModel = incomeViewModel
            ),
            isInTabletLandscape = windowSizeClass.isInTabletLandscapeView(),
            onNavigateBackToParent = { navController.popBackStack() }
        )
    }
}


@Composable
private fun addEditIncomeViewModel(
    owner: NavBackStackEntry,
    incomeViewModel: IncomeViewModel
): AddEditIncomeViewModel {
    val income: Income? = retrieveIncomeItem(
        navBackStackEntry = owner,
        incomeViewModel = incomeViewModel
    )

    val addEditIncomeViewModelFactory = EntryPointAccessors.fromActivity(
        activity = LocalContext.current as Activity,
        entryPoint = MainActivity.ViewModelFactoryProvider::class.java
    ).addEditIncomeViewModelFactory()

    return viewModel(
        factory = addEditIncomeViewModelFactory.create(
            owner = owner as SavedStateRegistryOwner,
            prevIncome = income
        )
    )
}

@Composable
private fun retrieveIncomeItem(
    navBackStackEntry: NavBackStackEntry,
    incomeViewModel: IncomeViewModel
): Income? {
    val incomeId: String =
        navBackStackEntry.arguments?.getString(RouteParamKey.ID) ?: UUID.randomUUID().toString()
    return incomeViewModel.getIncomeById(id = incomeId)
}


