package com.puzzle.industries.budgetplan.navigation.graphs

import android.app.Activity
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.composable
import androidx.navigation.navigation
import com.puzzle.industries.budgetplan.MainActivity
import com.puzzle.industries.budgetplan.ext.GetOnceResult
import com.puzzle.industries.budgetplan.ext.navigateAndClearStack
import com.puzzle.industries.budgetplan.localAuthRepoProvider
import com.puzzle.industries.budgetplan.navigation.constants.RouteParamKey
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.navigation.constants.ValueKey
import com.puzzle.industries.budgetplan.screens.registrationFlow.*
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.AuthViewModel
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.CountryCurrencyViewModel
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.RegistrationFlowViewModel
import dagger.hilt.android.EntryPointAccessors

@Composable
fun NavGraphBuilder.RegistrationGuideFlowGraph(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    registrationFlowViewModel: RegistrationFlowViewModel = viewModel()
) {
    navigation(startDestination = Routes.Auth.path, route = Routes.Registration.path) {
        authScreen(
            navController = navController,
            windowSizeClass = windowSizeClass,
            regFlowViewModel = registrationFlowViewModel
        )
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

        val countryCurrencyViewModel: CountryCurrencyViewModel = hiltViewModel()

        navController.GetOnceResult<String>(
            keyResult = ValueKey.COUNTRY_CURRENCY_KEY.name,
            onResult = { countryName ->
                val countryCurrency =
                    countryCurrencyViewModel.getCountryCurrencyByCountryName(countryName = countryName)
                countryCurrencyViewModel.publishValue(value = countryCurrency)
            }
        )

        CurrencySelectionScreen(
            viewModel = countryCurrencyViewModel,
            onCurrencySelectionClick = {
                navController.navigate(
                    route = Routes.CurrencyPicker.addParam(
                        key = RouteParamKey.ID,
                        value = countryCurrencyViewModel.sub.value.country
                    ).path
                )
            }) { countryCurrency ->
            regFlowViewModel.setCurrency(countryCurrency = countryCurrency)
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

            regFlowViewModel.register()
            navController.navigateAndClearStack(route = Routes.Main.path)
        }
    }
}

private fun NavGraphBuilder.budgetPlanGenerationDayScreen(
    navController: NavHostController,
    regFlowViewModel: RegistrationFlowViewModel
) {
    composable(route = Routes.PlanDay.path) {
        BudgetPlanGenerationDayScreen {
            //regFlowViewModel.setBudgetPlanGenerationDay(day = it)
            regFlowViewModel.register()

            navController.navigateAndClearStack(route = Routes.Main.path)
        }
    }
}

private fun NavGraphBuilder.authScreen(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass,
    regFlowViewModel: RegistrationFlowViewModel
) {

    composable(route = Routes.Auth.path) {
        val authRepoAssistedFactory = EntryPointAccessors.fromActivity(
            activity = LocalContext.current as Activity,
            entryPoint = MainActivity.ViewModelFactoryProvider::class.java
        ).authViewModelFactory()

        AuthScreen(
            authViewModel = viewModel(
                factory = AuthViewModel.provideFactory(
                    assistedFactory = authRepoAssistedFactory,
                    authRepo = localAuthRepoProvider.current
                )
            ),
            heightSizeClass = windowSizeClass.heightSizeClass
        ) {
            navController.navigate(route = Routes.Currency.path)
            //regFlowViewModel.setBudgetPlanGenerationDay(day = it)
            /*regFlowViewModel.register()

            navController.navigateAndClearStack(route = Routes.Main.path)*/
        }
    }
}
