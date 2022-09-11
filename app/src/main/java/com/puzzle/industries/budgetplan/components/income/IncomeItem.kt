package com.puzzle.industries.budgetplan.components.income

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Alarm
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
import com.puzzle.industries.budgetplan.data.IncomeDto
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.income.Income
import java.util.*


@Composable
@ExperimentalMaterial3Api
fun IncomeItem(income: IncomeDto, onClick: () -> Unit = {}) {
    Card(modifier = Modifier.fillMaxWidth(), onClick = onClick) {
        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.medium)) {
            Text(
                text = income.title,
                maxLines = 1,
                overflow = TextOverflow.Ellipsis,
                style = MaterialTheme.typography.titleMedium
            )

            if (income.description.isNotBlank()) {
                Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.small))
                Text(
                    text = income.description,
                    style = MaterialTheme.typography.bodySmall,
                    maxLines = 4,
                    overflow = TextOverflow.Ellipsis
                )
            }

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.desc_income),
                    tint = MaterialTheme.colorScheme.primary
                )

                Text(
                    text = stringResource(id = R.string.income_amount, "R", income.amount),
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.titleLarge
                )
            }

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))

            Row(verticalAlignment = Alignment.CenterVertically) {
                Icon(
                    modifier = Modifier.size(size = 12.dp),
                    imageVector = Icons.Rounded.Alarm,
                    contentDescription = stringResource(id = R.string.desc_income_frequency),
                    tint = MaterialTheme.colorScheme.secondary
                )

                Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.small))

                Text(
                    text = income.frequency,
                    color = MaterialTheme.colorScheme.secondary,
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
@ExperimentalMaterial3Api
@ExperimentalMaterial3WindowSizeClassApi
fun IncomeItemPreview() {
    BudgetPlanTheme(dynamicColor = false) {
        val income = IncomeDto(
            id = 0,
            title = "Some title",
            description = "some description about the income",
            amount = 13000.0,
            frequency = "Monthly",
        )
        IncomeItem(income) {}
    }

}