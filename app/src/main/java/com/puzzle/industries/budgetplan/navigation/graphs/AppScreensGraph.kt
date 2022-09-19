package com.puzzle.industries.budgetplan.navigation.graphs

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.lifecycle.viewmodel.compose.viewModel
import androidx.navigation.*
import androidx.navigation.compose.composable
import com.google.accompanist.pager.ExperimentalPagerApi
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.navigation.graphs.ext.navigateAndClearStack
import com.puzzle.industries.budgetplan.screens.WelcomeScreen
import com.puzzle.industries.budgetplan.screens.MainScreen
import com.puzzle.industries.budgetplan.screens.SplashScreen
import com.puzzle.industries.budgetplan.viewModels.SplashScreenViewModel
import com.puzzle.industries.budgetplan.viewModels.WelcomeMessagesViewModel


@Composable
@ExperimentalPagerApi
@ExperimentalMaterial3Api
@ExperimentalAnimationApi
fun appScreensGraph(
    navController: NavHostController,
    windowSizeClass: WindowSizeClass
): NavGraph {
    return navController.createGraph(startDestination = Routes.Splash.path) {
        splashScreen(navController = navController, viewModel = viewModel())
        welcomeScreen(navController = navController, viewModel = viewModel(), windowSizeClass = windowSizeClass)
        RegistrationGuideFlowGraph(navController = navController)

        composable(route = Routes.Main.path) {
            MainScreen()
        }

    }
}

private fun NavGraphBuilder.splashScreen(
    navController: NavHostController,
    viewModel: SplashScreenViewModel
) {
    composable(route = Routes.Splash.path) {
        splashScreen {
            val destinationPath: String =
                if (viewModel.isFirstTimeLaunch()) Routes.Welcome.path
                else Routes.Main.path

            navController.navigate(route = destinationPath) {
                clearAllPreviousRoutes(navController)
            }
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