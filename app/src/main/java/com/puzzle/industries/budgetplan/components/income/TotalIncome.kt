package com.puzzle.industries.budgetplan.components.income

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
fun TotalIncome(
    modifier: Modifier = Modifier,
    currencySymbol: String,
    totalIncome: Double
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Icon(
            imageVector = Icons.Rounded.Money,
            contentDescription = stringResource(id = R.string.desc_income),
            tint = MaterialTheme.colorScheme.primary
        )

        H_S_Space()

        Text(
            text = stringResource(id = R.string.currency_amount, currencySymbol, totalIncome),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.labelLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
@ExperimentalMaterial3WindowSizeClassApi
private fun TotalIncomePreview() {
    BudgetPlanTheme(dynamicColor = false) {
        TotalIncome(currencySymbol = "R", totalIncome = 12555.0)
    }
}