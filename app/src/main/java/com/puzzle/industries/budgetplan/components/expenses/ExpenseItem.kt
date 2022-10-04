package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.components.layout.MiniCaption
import com.puzzle.industries.budgetplan.components.layout.ModifiableItemWrapper
import com.puzzle.industries.budgetplan.components.layout.TitleAndDescription
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
private fun ExpenseItem(
    modifier: Modifier,
    title: String,
    amount: Double,
    frequency: String
) {
    ModifiableItemWrapper(modifier = modifier, {}, {}) {
        Column(modifier = it) {

            TitleAndDescription(title = title)

            V_M_Space()

            OutcomeAmount(amount = amount)

            V_M_Space()

            MiniCaption(imageVector = Icons.Rounded.Alarm, message = frequency)
        }
    }
}

@Composable
@Preview
@ExperimentalMaterial3WindowSizeClassApi
fun ExpenseItemPreview(){
    BudgetPlanTheme(dynamicColor = false) {
        ExpenseItem(modifier = Modifier.fillMaxWidth(), title = "Expense Item", amount = 13.0, frequency = "Monthly")
    }
}