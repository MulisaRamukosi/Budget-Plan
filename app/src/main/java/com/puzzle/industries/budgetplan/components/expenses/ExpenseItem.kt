package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.MiniCaption
import com.puzzle.industries.budgetplan.components.ModifiableItemWrapper
import com.puzzle.industries.budgetplan.components.TitleAndDescription
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
private fun ExpenseItem(
    modifier: Modifier,
    title: String,
    amount: Double,
    frequency: String
) {
    ModifiableItemWrapper(modifier = modifier) {
        Column(modifier = it) {

            TitleAndDescription(title = title)

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))

            OutcomeAmount(amount = amount)

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))

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