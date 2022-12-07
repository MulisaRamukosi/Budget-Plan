@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.puzzle.industries.budgetplan

import android.content.Intent
import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.puzzle.industries.budgetplan.factory.viewModel.AddEditExpenseGroupViewModelAssistedFactory
import com.puzzle.industries.budgetplan.factory.viewModel.AddEditExpenseViewModelAssistedFactory
import com.puzzle.industries.budgetplan.factory.viewModel.AddEditIncomeViewModelAssistedFactory
import com.puzzle.industries.budgetplan.factory.viewModel.AuthViewModelAssistedFactory
import com.puzzle.industries.budgetplan.navigation.AppScreensNavHost
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.domain.constants.ThemeType
import com.puzzle.industries.domain.datastores.ThemeDataStore
import com.puzzle.industries.domain.services.AuthService
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import javax.inject.Inject

val localAuthService = staticCompositionLocalOf<AuthService> {
    error("to be provided")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themePreferences: ThemeDataStore
    @Inject
    lateinit var authService: AuthService

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun addEditIncomeViewModelFactory(): AddEditIncomeViewModelAssistedFactory
        fun addEditExpenseGroupViewModelFactory(): AddEditExpenseGroupViewModelAssistedFactory
        fun addEditExpenseViewModelFactory(): AddEditExpenseViewModelAssistedFactory
        fun authServiceViewModelFactory(): AuthViewModelAssistedFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        authService.initAuth()

        setContent {
            CompositionLocalProvider(localAuthService provides authService) {
                val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(activity = this)
                val themeType by themePreferences.getSelectedTheme()
                    .collectAsState(initial = ThemeType.SYSTEM_DEPENDENT)

                BudgetPlanTheme(themeType = themeType) {
                    Surface(
                        modifier = Modifier.fillMaxSize(),
                        color = MaterialTheme.colorScheme.background
                    ) {
                        Column(modifier = Modifier.fillMaxSize()) {
                            AppScreensNavHost(
                                modifier = Modifier.weight(weight = 1f),
                                windowSizeClass = windowSizeClass
                            )
                        }
                    }
                }
            }

        }
    }

}