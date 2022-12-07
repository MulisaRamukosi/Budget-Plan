package com.puzzle.industries.budgetplan.navigation.graphs

import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
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
import com.puzzle.industries.budgetplan.screens.registrationFlow.WelcomeScreen
import com.puzzle.industries.budgetplan.viewModels.intro.SplashScreenViewModel
import com.puzzle.industries.budgetplan.viewModels.intro.WelcomeMessagesViewModel
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.CountryCurrencyViewModel
import com.puzzle.industries.domain.constants.CountryCurrencyConfig


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
            MainScreen(
                mainNavController = navController,
                windowSizeClass = windowSizeClass
            )
        }

    }
}

private fun NavGraphBuilder.countryPickerScreen(
    navController: NavHostController,
    viewModel: CountryCurrencyViewModel
) {
    composable(
        route = Routes.CurrencyPicker.path,
        arguments = listOf(navArgument(name = RouteParamKey.ID) { type = NavType.StringType })
    ) { navBackStackEntry ->

        val countryName = navBackStackEntry.arguments?.getString(RouteParamKey.ID) ?: CountryCurrencyConfig.DEFAULT_COUNTRY
        val selectedCountryCurrency by viewModel.sub.collectAsState(
            initial = viewModel.getCountryCurrencyByCountryName(
                countryName = countryName
            )
        )
        viewModel.publishValue(value = selectedCountryCurrency)

        navController.SetResult(key = ValueKey.COUNTRY_CURRENCY_KEY.name, value = selectedCountryCurrency.country)
        CountryPickerScreen(countryCurrencyViewModel = viewModel, onDoneClick = { navController.popBackStack() })
    }
}

private fun NavGraphBuilder.splashScreen(
    navController: NavHostController,
    viewModel: SplashScreenViewModel
) {
    composable(route = Routes.Splash.path) {
        SplashScreen {
            val destinationPath: String =
                if (viewModel.isFirstTimeLaunch) Routes.Welcome.path
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

        val currentPage by viewModel.currentPage.collectAsState()

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