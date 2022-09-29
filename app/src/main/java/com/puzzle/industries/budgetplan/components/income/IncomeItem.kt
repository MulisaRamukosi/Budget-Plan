package com.puzzle.industries.budgetplan.components.income

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.puzzle.industries.budgetplan.components.MiniCaption
import com.puzzle.industries.budgetplan.components.ModifiableItemWrapper
import com.puzzle.industries.budgetplan.components.TitleAndDescription
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.util.buildFrequencyMessage
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.income.Income
import java.util.*

@Composable
@ExperimentalMaterial3Api
fun IncomeItem(
    income: Income,
    currencySymbol: String,
    onEditClick: (Income) -> Unit,
    onDeleteClick: (Income) -> Unit
) {
    ModifiableItemWrapper(
        modifier = Modifier.fillMaxWidth(),
        onEditClick = { onEditClick(income) },
        onDeleteClick = { onDeleteClick(income) }
    ) {
        Column(modifier = it) {

            TitleAndDescription(title = income.title, description = income.description)

            V_S_Space()

            IncomeAmount(amount = income.amount, currencySymbol = currencySymbol)

            V_M_Space()

            MiniCaption(imageVector = Icons.Rounded.Alarm, message = buildFrequencyMessage(
                type = income.frequencyType,
                message = income.frequencyWhen
            ))
        }
    }
}

@Composable
private fun IncomeAmount(amount: Double, currencySymbol: String) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.Add,
            contentDescription = stringResource(id = R.string.desc_income),
            tint = MaterialTheme.colorScheme.primary
        )

        Text(
            text = stringResource(id = R.string.currency_amount, currencySymbol, amount),
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
        val income = Income(
            id = UUID.randomUUID(),
            title = "Some title",
            description = "some description about the income",
            amount = 13000.0,
            frequencyType = FrequencyType.MONTHLY,
            frequencyWhen = "2"
        )
        IncomeItem(income, currencySymbol = "R", {}, {})
    }

}