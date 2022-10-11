@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class)

package com.puzzle.industries.budgetplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.WindowSizeClass
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import androidx.compose.ui.Modifier
import com.puzzle.industries.budgetplan.factory.viewModel.AddEditExpenseGroupViewModelAssistedFactory
import com.puzzle.industries.budgetplan.factory.viewModel.AddEditExpenseViewModelAssistedFactory
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
        fun addEditExpenseGroupViewModelFactory(): AddEditExpenseGroupViewModelAssistedFactory
        fun addEditExpenseViewModelFactory(): AddEditExpenseViewModelAssistedFactory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            val windowSizeClass: WindowSizeClass = calculateWindowSizeClass(activity = this)
            BudgetPlanTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background
                ) {
                    AppScreensNavHost(windowSizeClass = windowSizeClass)
                }

            }
        }
    }
}