package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
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
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun ExpenseItem(modifier: Modifier = Modifier){
    Column(modifier = modifier) {
        Row{
            Text(
                modifier = Modifier.weight(1f),
                text = "Expense Item",
                maxLines = 1,
                style = MaterialTheme.typography.bodyMedium,
                overflow = TextOverflow.Ellipsis
            )

            Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))

            Text(
                text = "R13",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.bodyLarge
            )
        }

        Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.extraSmall))

        Row(verticalAlignment = Alignment.CenterVertically) {
            Icon(
                modifier = Modifier.size(size = 12.dp),
                imageVector = Icons.Rounded.Alarm,
                contentDescription = stringResource(id = R.string.desc_expense_frequency),
                tint = MaterialTheme.colorScheme.secondary
            )

            Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.small))

            Text(
                text = "monthly",
                color = MaterialTheme.colorScheme.secondary,
                style = MaterialTheme.typography.bodySmall
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
@ExperimentalMaterial3WindowSizeClassApi
fun ExpenseItemPreview(){
    BudgetPlanTheme(dynamicColor = false) {
        ExpenseItem()
    }
}