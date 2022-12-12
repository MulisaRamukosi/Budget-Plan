@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.puzzle.industries.budgetplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
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
import com.puzzle.industries.domain.repository.user.AuthRepository
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent
import dagger.hilt.android.components.ActivityRetainedComponent
import javax.inject.Inject

val localAuthRepoProvider = staticCompositionLocalOf<AuthRepository> {
    error("not provided yet")
}

@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @Inject
    lateinit var themePreferences: ThemeDataStore

    @Inject
    lateinit var authRepository: AuthRepository

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun addEditIncomeViewModelFactory(): AddEditIncomeViewModelAssistedFactory
        fun addEditExpenseGroupViewModelFactory(): AddEditExpenseGroupViewModelAssistedFactory
        fun addEditExpenseViewModelFactory(): AddEditExpenseViewModelAssistedFactory
        fun authViewModelFactory(): AuthViewModelAssistedFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        setContent {
            val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(activity = this)
            val themeType by themePreferences.getSelectedTheme()
                .collectAsState(initial = ThemeType.SYSTEM_DEPENDENT)

            CompositionLocalProvider(localAuthRepoProvider provides authRepository) {
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