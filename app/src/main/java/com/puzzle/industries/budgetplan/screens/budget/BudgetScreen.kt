package com.puzzle.industries.budgetplan.screens.budget

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun BudgetScreen(){
    Content()
}

@Composable
private fun Content(){
    Surface(modifier = Modifier.fillMaxSize()) {
        Text(text = "Budget")
    }
}