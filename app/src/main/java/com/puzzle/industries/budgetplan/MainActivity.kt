@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.puzzle.industries.budgetplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.puzzle.industries.budgetplan.factory.viewModel.AddEditIncomeViewModelAssistedFactory
import com.puzzle.industries.budgetplan.navigation.AppScreensNavHost
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.components.ActivityComponent


@AndroidEntryPoint
class MainActivity : ComponentActivity() {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun addEditIncomeViewModelFactory(): AddEditIncomeViewModelAssistedFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(activity = this)
            BudgetPlanTheme {
                AppScreensNavHost(windowSizeClass = windowSizeClass)
            }
        }
    }
}