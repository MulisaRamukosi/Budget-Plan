package com.puzzle.industries.budgetplan.components.income

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.Spacer
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Payments
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
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun TotalIncome(
    modifier: Modifier = Modifier,
    income: Double
) {
    Row(
        modifier = modifier,
        verticalAlignment = Alignment.CenterVertically,

    ) {
        Icon(
            imageVector = Icons.Rounded.Payments,
            contentDescription = stringResource(id = R.string.desc_income),
            tint = MaterialTheme.colorScheme.primary
        )

        Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.small))

        Text(
            text = stringResource(id = R.string.currency_amount, "R", income),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.bodyLarge
        )
    }
}

@Preview(showBackground = true)
@Composable
@ExperimentalMaterial3WindowSizeClassApi
private fun TotalIncomePreview() {
    BudgetPlanTheme(dynamicColor = false) {
        TotalIncome(income = 12555.0)
    }
}