package com.puzzle.industries.budgetplan.navigation.graphs

import android.app.Activity
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.puzzle.industries.budgetplan.MainActivity
import com.puzzle.industries.budgetplan.ext.isInTabletLandscapeView
import com.puzzle.industries.budgetplan.navigation.constants.RouteParamKey
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.screens.editing.AddEditIncomeScreen
import com.puzzle.industries.budgetplan.screens.home.BudgetScreen
import com.puzzle.industries.budgetplan.viewModels.budget.AddEditIncomeViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.IncomeViewModel
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
        addEditIncomeScreen(navController = navController, windowSizeClass = windowSizeClass, incomeViewModel = incomeViewModel)
    }
}

private fun NavGraphBuilder.budgetScreen(
    navController: NavController,
    incomeViewModel: IncomeViewModel
){
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
        val income: Income? = retrieveIncomeItem(navBackStackEntry, incomeViewModel)
        AddEditIncomeScreen(
            incomeViewModel = incomeViewModel,
            addEditIncomeViewModel = addEditIncomeViewModel(income = income),
            isInTabletLandscape = windowSizeClass.isInTabletLandscapeView(),
            onNavigateBackToParent = { navController.popBackStack() }
        )

    }
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

@Composable
private fun addEditIncomeViewModel(income: Income?): AddEditIncomeViewModel {
    val addEditIncomeViewModelFactory = EntryPointAccessors.fromActivity(
        activity = LocalContext.current as Activity,
        entryPoint = MainActivity.ViewModelFactoryProvider::class.java
    ).addEditIncomeViewModelFactory()
    return viewModel(
        factory = AddEditIncomeViewModel.provideFactory(
            assistedFactory = addEditIncomeViewModelFactory,
            prevIncome = income
        )
    )
}


