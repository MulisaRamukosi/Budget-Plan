package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.CheckboxButton
import com.puzzle.industries.budgetplan.components.divider.HorizontalDivider
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun PendingExpenses(modifier: Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .padding(horizontal = MaterialTheme.spacing.medium)
                .padding(top = MaterialTheme.spacing.medium)
        ) {
            Text(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.pending_expenses),
                style = MaterialTheme.typography.bodyLarge
            )

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))

            PendingExpense(checked = true)
            PendingExpense(checked = false)

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))

            TotalExpensesAmount(currency = "R", amount = 1000.0)

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.small))

            HorizontalDivider()
            AddUnplannedExpense()

        }
    }
}

@Composable
private fun AddUnplannedExpense() {
    TextButton(onClick = { }) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = stringResource(id = R.string.add_unplanned_expense),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.tertiary
            )
            Icon(
                imageVector = Icons.Rounded.ChevronRight,
                contentDescription = stringResource(id = R.string.desc_arrow_right_icon),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun TotalExpensesAmount(currency: String, amount: Double) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Text(
            modifier = Modifier.weight(weight = 1f),
            text = stringResource(id = R.string.total),
            style = MaterialTheme.typography.bodySmall
        )

        Text(
            text = stringResource(id = R.string.currency_amount, currency, amount),
            style = MaterialTheme.typography.bodyLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun PendingExpense(checked: Boolean) {
    CheckboxButton(
        checked = checked,
        onCheckChanged = {}
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(
                modifier = Modifier.weight(1f),
                text = "Rent",
                style = MaterialTheme.typography.bodyMedium,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis
            )
            Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))
            Text(
                text = stringResource(id = R.string.currency_amount, "R", 320.0),
                style = MaterialTheme.typography.bodySmall,
                color = MaterialTheme.colorScheme.primary
            )
        }
    }
}

@Preview
@Composable
@ExperimentalMaterial3WindowSizeClassApi
fun PreviewPendingExpenses() {
    BudgetPlanTheme(dynamicColor = false) {
        PendingExpenses(modifier = Modifier)
    }
}

