package com.puzzle.industries.budgetplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.puzzle.industries.budgetplan.navigation.AppScreensNavHost
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@ExperimentalMaterial3Api
@ExperimentalMaterial3WindowSizeClassApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetPlanTheme(windowSizeClass = calculateWindowSizeClass(activity = this)) {
                AppScreensNavHost()
            }
        }
    }
}