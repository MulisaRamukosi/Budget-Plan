package com.puzzle.industries.budgetplan.screens.budget

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.components.expenses.ExpenseGroupItem
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@ExperimentalAnimationApi
@Composable
fun ExpenseScreen(){
    Column(
        Modifier
            .padding(all = 16.dp)
            .fillMaxSize()
            .verticalScroll(state = rememberScrollState())
    ){
        repeat(5){
            ExpenseGroupItem()
            Spacer(modifier = Modifier.height(height = 16.dp))
        }
    }
}

@Composable
private fun Content(){

}

@Composable
@Preview(showBackground = true)
@ExperimentalAnimationApi
@ExperimentalMaterial3WindowSizeClassApi
private fun ExpenseScreenPreview(){
    BudgetPlanTheme(dynamicColor = false) {
        ExpenseScreen()
    }
}