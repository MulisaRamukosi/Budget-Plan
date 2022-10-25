package com.puzzle.industries.budgetplan.navigation.graphs

import android.app.Activity
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import androidx.savedstate.SavedStateRegistryOwner
import com.puzzle.industries.budgetplan.MainActivity
import com.puzzle.industries.budgetplan.ext.isInTabletLandscapeView
import com.puzzle.industries.budgetplan.navigation.constants.RouteParamKey
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.screens.budget.expense.AddEditExpenseGroupScreen
import com.puzzle.industries.budgetplan.screens.budget.expense.AddEditExpenseScreen
import com.puzzle.industries.budgetplan.screens.budget.income.AddEditIncomeScreen
import com.puzzle.industries.budgetplan.screens.budget.reminder.AddReminderScreen
import com.puzzle.industries.budgetplan.screens.home.BudgetScreen
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.AddEditExpenseGroupViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.AddEditExpenseViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.ExpenseViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.AddEditIncomeViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.reminder.ReminderViewModel
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.income.Income
import dagger.hilt.android.EntryPointAccessors
import java.util.*

fun NavGraphBuilder.budgetScreensGraph(
    navController: NavController,
    windowSizeClass: WindowSizeClass,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
    reminderViewModel: ReminderViewModel
) {
    navigation(startDestination = Routes.Budget.path, route = Routes.BudgetRoute.path) {
        budgetScreen(
            navController = navController,
            incomeViewModel = incomeViewModel,
            expenseViewModel = expenseViewModel,
            reminderViewModel = reminderViewModel
        )

        addEditIncomeScreen(
            navController = navController,
            windowSizeClass = windowSizeClass,
            incomeViewModel = incomeViewModel
        )

        addEditExpenseGroupScreen(
            navController = navController,
            windowSizeClass = windowSizeClass,
            expenseViewModel = expenseViewModel
        )

        addEditExpenseScreen(
            navController = navController,
            windowSizeClass = windowSizeClass,
            expenseViewModel = expenseViewModel
        )

        addReminderScreen(
            navController = navController,
            windowSizeClass = windowSizeClass,
            reminderViewModel = reminderViewModel
        )
    }
}


private fun NavGraphBuilder.budgetScreen(
    navController: NavController,
    incomeViewModel: IncomeViewModel,
    expenseViewModel: ExpenseViewModel,
    reminderViewModel: ReminderViewModel
) {
    composable(route = Routes.Budget.path) {
        BudgetScreen(
            navController = navController,
            incomeViewModel = incomeViewModel,
            expenseViewModel = expenseViewModel,
            reminderViewModel = reminderViewModel
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
            addEditIncomeViewModel = buildAddEditIncomeViewModel(
                owner = navBackStackEntry,
                incomeViewModel = incomeViewModel
            ),
            isInTabletLandscape = windowSizeClass.isInTabletLandscapeView(),
            onNavigateBackToParent = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.addEditExpenseGroupScreen(
    navController: NavController,
    windowSizeClass: WindowSizeClass,
    expenseViewModel: ExpenseViewModel
) {
    composable(
        route = Routes.AddEditExpenseGroup.path,
        arguments = listOf(
            navArgument(name = RouteParamKey.ID) { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        AddEditExpenseGroupScreen(
            expenseViewModel = expenseViewModel,
            addEditExpenseGroupViewModel = buildAddEditExpenseGroupViewModel(
                owner = navBackStackEntry,
                expenseViewModel = expenseViewModel
            ),
            isInTabletLandscape = windowSizeClass.isInTabletLandscapeView(),
            onNavigateBackToParent = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.addEditExpenseScreen(
    navController: NavController,
    windowSizeClass: WindowSizeClass,
    expenseViewModel: ExpenseViewModel
) {
    composable(
        route = Routes.AddEditExpense.path,
        arguments = listOf(
            navArgument(name = RouteParamKey.EXPENSE_GROUP_ID) { type = NavType.StringType },
            navArgument(name = RouteParamKey.ID) { type = NavType.StringType }
        )
    ) { navBackStackEntry ->
        AddEditExpenseScreen(
            expenseViewModel = expenseViewModel,
            addEditExpenseViewModel = buildAddEditExpenseViewModel(
                owner = navBackStackEntry,
                expenseViewModel = expenseViewModel
            ),
            isInTabletLandscape = windowSizeClass.isInTabletLandscapeView(),
            onNavigateBackToParent = { navController.popBackStack() }
        )
    }
}

fun NavGraphBuilder.addReminderScreen(
    navController: NavController,
    windowSizeClass: WindowSizeClass,
    reminderViewModel: ReminderViewModel
) {
    composable(
        route = Routes.AddReminder.path
    ) {
        AddReminderScreen(
            reminderViewModel = reminderViewModel,
            addReminderViewModel = hiltViewModel(),
            isInTabletLandscape = windowSizeClass.isInTabletLandscapeView(),
            onNavigateBackToParent = { navController.popBackStack() }
        )
    }
}

@Composable
private fun buildAddEditIncomeViewModel(
    owner: NavBackStackEntry,
    incomeViewModel: IncomeViewModel
): AddEditIncomeViewModel {

    val incomeId: String =
        owner.arguments?.getString(RouteParamKey.ID) ?: UUID.randomUUID().toString()

    val income: Income? = incomeViewModel.getIncomeById(id = UUID.fromString(incomeId))

    val addEditIncomeViewModelAssistedFactory = EntryPointAccessors.fromActivity(
        activity = LocalContext.current as Activity,
        entryPoint = MainActivity.ViewModelFactoryProvider::class.java
    ).addEditIncomeViewModelFactory()

    return viewModel(
        factory = addEditIncomeViewModelAssistedFactory.create(
            owner = owner as SavedStateRegistryOwner,
            prevIncome = income
        )
    )
}

@Composable
private fun buildAddEditExpenseGroupViewModel(
    owner: NavBackStackEntry,
    expenseViewModel: ExpenseViewModel
): AddEditExpenseGroupViewModel {
    val expenseGroupId: String =
        owner.arguments?.getString(RouteParamKey.ID) ?: UUID.randomUUID().toString()
    val expenseGroup: ExpenseGroup? =
        expenseViewModel.getExpenseGroupById(expenseGroupId = UUID.fromString(expenseGroupId))

    val addEditExpenseGroupViewModelAssistedFactory = EntryPointAccessors.fromActivity(
        activity = LocalContext.current as Activity,
        entryPoint = MainActivity.ViewModelFactoryProvider::class.java
    ).addEditExpenseGroupViewModelFactory()

    return viewModel(
        factory = addEditExpenseGroupViewModelAssistedFactory.create(
            owner = owner as SavedStateRegistryOwner,
            prevExpenseGroup = expenseGroup
        )
    )
}

@Composable
private fun buildAddEditExpenseViewModel(
    owner: NavBackStackEntry,
    expenseViewModel: ExpenseViewModel
): AddEditExpenseViewModel {
    val expenseGroupId: String =
        owner.arguments?.getString(RouteParamKey.EXPENSE_GROUP_ID) ?: UUID.randomUUID().toString()
    val expenseId: String =
        owner.arguments?.getString(RouteParamKey.ID) ?: UUID.randomUUID().toString()
    val expense = expenseViewModel.getExpenseById(expenseId = UUID.fromString(expenseId))

    val addEditExpenseViewModelAssistedFactory = EntryPointAccessors.fromActivity(
        activity = LocalContext.current as Activity,
        entryPoint = MainActivity.ViewModelFactoryProvider::class.java
    ).addEditExpenseViewModelFactory()

    return viewModel(
        factory = addEditExpenseViewModelAssistedFactory.create(
            owner = owner as SavedStateRegistryOwner,
            expenseGroupId = UUID.fromString(expenseGroupId),
            prevExpense = expense
        )
    )
}




