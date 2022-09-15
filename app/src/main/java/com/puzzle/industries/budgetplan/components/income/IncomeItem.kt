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
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.MiniCaption
import com.puzzle.industries.budgetplan.components.ModifiableItemWrapper
import com.puzzle.industries.budgetplan.components.TitleAndDescription
import com.puzzle.industries.budgetplan.data.IncomeDto
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
@ExperimentalMaterial3Api
fun IncomeItem(income: IncomeDto, onClick: () -> Unit = {}) {
    ModifiableItemWrapper(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = it) {
            TitleAndDescription(title = income.title, description = income.description)
            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))
            IncomeAmount(amount = income.amount)
            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))
            MiniCaption(imageVector = Icons.Rounded.Alarm, message = income.frequency)
        }
    }
}

@Composable
private fun IncomeAmount(amount: Double) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = R.string.desc_income),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(id = R.string.currency_amount, "R", amount),
            color = MaterialTheme.colorScheme.primary,
            style = MaterialTheme.typography.headlineSmall
        )
    }
}



@Composable
@Preview
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