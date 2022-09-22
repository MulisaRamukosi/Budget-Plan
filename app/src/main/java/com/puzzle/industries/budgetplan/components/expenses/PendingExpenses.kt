package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.CheckCircle
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.CheckboxButton
import com.puzzle.industries.budgetplan.components.divider.HorizontalDivider
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.components.text.HomeCardTitle
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun PendingExpenses(modifier: Modifier) {
    Card(modifier = modifier) {
        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium)
                .padding(top = MaterialTheme.spacing.medium)
        ) {
            HomeCardTitle(
                modifier = Modifier.Companion.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.pending_expenses)
            )

            V_S_Space()
            Text(
                text = stringResource(id = R.string.note_delete_expense),
                style = MaterialTheme.typography.labelSmall
            )

            V_M_Space()

            PendingExpenseItems()
            //AllExpensesPaidView(modifier = Modifier.fillMaxWidth())

            V_S_Space()

            HorizontalDivider()

            AddUnplannedExpense()


        }
    }
}

@Composable
private fun AllExpensesPaidView(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Rounded.CheckCircle,
            contentDescription = stringResource(id = R.string.desc_check_icon),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(size = 64.dp)
        )
        V_S_Space()
        Text(
            text = stringResource(id = R.string.all_expenses_paid),
            color = MaterialTheme.colorScheme.secondary
        )
    }
}

@Composable
private fun PendingExpenseItems() {
    PendingExpense(checked = true)
    PendingExpense(checked = false)

    V_M_Space()

    TotalExpensesAmount(currency = "R", amount = 1000.0)
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
        PendingExpenses(modifier = Modifier.fillMaxWidth())
    }
}

