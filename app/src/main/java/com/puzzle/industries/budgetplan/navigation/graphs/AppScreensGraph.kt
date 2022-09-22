package com.puzzle.industries.budgetplan.navigation.graphs

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.puzzle.industries.budgetplan.ext.SetResult
import com.puzzle.industries.budgetplan.ext.navigateAndClearStack
import com.puzzle.industries.budgetplan.navigation.constants.RouteParamKey
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.navigation.constants.ValueKey
import com.puzzle.industries.budgetplan.screens.CountryPickerScreen
import com.puzzle.industries.budgetplan.screens.MainScreen
import com.puzzle.industries.budgetplan.screens.SplashScreen
import com.puzzle.industries.budgetplan.screens.WelcomeScreen
import com.puzzle.industries.budgetplan.viewModels.SplashScreenViewModel
import com.puzzle.industries.budgetplan.viewModels.WelcomeMessagesViewModel
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.CurrencyViewModel


@Composable
fun appScreensGraph(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
): NavGraph {
    return navController.createGraph(startDestination = Routes.Splash.path) {
        splashScreen(navController = navController, viewModel = viewModel())

        welcomeScreen(
            navController = navController,
            viewModel = viewModel(),
            windowSizeClass = windowSizeClass
        )

        countryPickerScreen(navController = navController, viewModel = viewModel())

        RegistrationGuideFlowGraph(navController = navController)

        composable(route = Routes.Main.path) {
            MainScreen(windowSizeClass = windowSizeClass)
        }

    }
}

private fun NavGraphBuilder.countryPickerScreen(
    navController: NavHostController,
    viewModel: CurrencyViewModel
) {
    composable(
        route = Routes.CurrencyPicker.path,
        arguments = listOf(navArgument(name = RouteParamKey.ID) { type = NavType.IntType })
    ) { navBackStackEntry ->

        val id = navBackStackEntry.arguments?.getInt(RouteParamKey.ID)
        val selectedId by viewModel.pub.observeAsState(initial = id ?: 0)
        viewModel.pub.value = selectedId

        navController.SetResult(key = ValueKey.COUNTRY_CURRENCY_KEY.name, value = selectedId)
        CountryPickerScreen(viewModel = viewModel, onDoneClick = { navController.popBackStack() })
    }
}

private fun NavGraphBuilder.splashScreen(
    navController: NavHostController,
    viewModel: SplashScreenViewModel
) {
    composable(route = Routes.Splash.path) {
        SplashScreen {
            val destinationPath: String =
                if (viewModel.isFirstTimeLaunch()) Routes.Welcome.path
                else Routes.Main.path

            navController.navigateAndClearStack(route = destinationPath)
        }
    }
}

private fun NavGraphBuilder.welcomeScreen(
    navController: NavHostController,
    viewModel: WelcomeMessagesViewModel,
    windowSizeClass: WindowSizeClass
) {
    composable(route = Routes.Welcome.path) {

        val currentPage by viewModel.getCurrentPage().observeAsState(initial = 0)

        WelcomeScreen(
            currentPage = currentPage,
            numOfPages = viewModel.getNumOfPages(),
            currentWelcomeMessage = viewModel.getCurrentWelcomeMessage(),
            windowSizeClass = windowSizeClass
        ) {
            val navigatedToNextPage: Boolean = viewModel.navigateToNextMessage()
            if (!navigatedToNextPage) {
                navController.navigate(route = Routes.Registration.path)
            }
        }
    }
}