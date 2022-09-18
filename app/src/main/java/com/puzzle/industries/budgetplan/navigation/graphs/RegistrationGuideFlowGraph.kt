package com.puzzle.industries.budgetplan.navigation.graphs

import androidx.compose.runtime.Composable
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.navigation.graphs.ext.clearAllPreviousRoutes
import com.puzzle.industries.budgetplan.screens.intro.registrationGuideFlow.CurrencySelectionScreen
import com.puzzle.industries.budgetplan.screens.intro.registrationGuideFlow.DebtScreen
import com.puzzle.industries.budgetplan.screens.intro.registrationGuideFlow.IncomeInputScreen

@Composable
fun NavGraphBuilder.RegistrationGuideFlowGraph(navController: NavHostController) {
    navigation(startDestination = Routes.Currency.path, route = Routes.Setup.path ){
        currencyScreen(navController = navController)
        incomeScreen(navController = navController)
        debtScreen(navController = navController)
    }
}

private fun NavGraphBuilder.currencyScreen(navController: NavHostController){
    composable(route = Routes.Currency.path){
        CurrencySelectionScreen {
            //TODO: save country currency
            navController.navigate(route = Routes.Income.path)
        }
    }
}

private fun NavGraphBuilder.incomeScreen(navController: NavHostController){
    composable(route = Routes.Income.path){
        IncomeInputScreen{
            //TODO: save initial income
            navController.navigate(route = Routes.Debt.path)
        }
    }
}

private fun NavGraphBuilder.debtScreen(navController: NavHostController){
    composable(route = Routes.Debt.path) {
        DebtScreen {
            //TODO: save allow debt state
            navController.navigate(route = Routes.Main.path){
                clearAllPreviousRoutes(navController)
            }
        }
    }
}
