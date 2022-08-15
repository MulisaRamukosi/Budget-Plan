package com.puzzle.industries.budgetplan

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.material3.windowsizeclass.calculateWindowSizeClass
import com.puzzle.industries.budgetplan.navigation.MainNavigation
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import dagger.hilt.android.AndroidEntryPoint


@AndroidEntryPoint
@ExperimentalMaterial3WindowSizeClassApi
class MainActivity : ComponentActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContent {
            BudgetPlanTheme(windowSizeClass = calculateWindowSizeClass(activity = this)) {
                MainNavigation()
            }
        }
    }
}