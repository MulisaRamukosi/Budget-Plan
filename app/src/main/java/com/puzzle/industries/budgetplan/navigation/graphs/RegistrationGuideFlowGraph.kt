package com.puzzle.industries.budgetplan.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.puzzle.industries.budgetplan.ext.GetOnceResult
import com.puzzle.industries.budgetplan.ext.navigateAndClearStack
import com.puzzle.industries.budgetplan.navigation.constants.RouteParamKey
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.navigation.constants.ValueKey
import com.puzzle.industries.budgetplan.screens.registration.BudgetPlanGenerationDayScreen
import com.puzzle.industries.budgetplan.screens.registration.CurrencySelectionScreen
import com.puzzle.industries.budgetplan.screens.registration.DebtScreen
import com.puzzle.industries.budgetplan.screens.registration.IncomeInputScreen
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.CurrencyViewModel
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.RegistrationFlowViewModel

@Composable
fun NavGraphBuilder.RegistrationGuideFlowGraph(
    navController: NavHostController,
    registrationFlowViewModel: RegistrationFlowViewModel = viewModel()
) {
    navigation(startDestination = Routes.Currency.path, route = Routes.Registration.path) {
        currencyScreen(navController = navController, regFlowViewModel = registrationFlowViewModel)
        incomeScreen(navController = navController, regFlowViewModel = registrationFlowViewModel)
        debtScreen(navController = navController, regFlowViewModel = registrationFlowViewModel)
        budgetPlanGenerationDayScreen(
            navController = navController,
            regFlowViewModel = registrationFlowViewModel
        )
    }
}

private fun NavGraphBuilder.currencyScreen(
    navController: NavHostController,
    regFlowViewModel: RegistrationFlowViewModel
) {
    composable(route = Routes.Currency.path) {

        val currencyViewModel: CurrencyViewModel = viewModel()

        navController.GetOnceResult<Int>(
            keyResult = ValueKey.COUNTRY_CURRENCY_KEY.name,
            onResult = { currencyId ->
                currencyViewModel.pub.value = currencyId
            }
        )

        CurrencySelectionScreen(
            viewModel = currencyViewModel,
            onCurrencySelectionClick = {
                navController.navigate(
                    route = Routes.CurrencyPicker.addParam(
                        key = RouteParamKey.ID,
                        value = currencyViewModel.sub.value.toString()
                    ).path
                )
            }) {

            regFlowViewModel.setCurrency(countryCurrency = it)
            navController.navigate(route = Routes.Income.path)
        }
    }
}

private fun NavGraphBuilder.incomeScreen(
    navController: NavHostController,
    regFlowViewModel: RegistrationFlowViewModel
) {
    composable(route = Routes.Income.path) {
        IncomeInputScreen {
            regFlowViewModel.setIncome(income = it)
            navController.navigate(route = Routes.Debt.path)
        }
    }
}

private fun NavGraphBuilder.debtScreen(
    navController: NavHostController,
    regFlowViewModel: RegistrationFlowViewModel
) {
    composable(route = Routes.Debt.path) {
        DebtScreen {
            regFlowViewModel.setDebtAllowed(debtAllowed = it)
            navController.navigate(route = Routes.PlanDay.path)
        }
    }
}

private fun NavGraphBuilder.budgetPlanGenerationDayScreen(
    navController: NavHostController,
    regFlowViewModel: RegistrationFlowViewModel
) {
    composable(route = Routes.PlanDay.path) {
        BudgetPlanGenerationDayScreen {
            regFlowViewModel.setBudgetPlanGenerationDay(day = it)
            regFlowViewModel.register()

            navController.navigateAndClearStack(route = Routes.Main.path)
        }
    }
}
