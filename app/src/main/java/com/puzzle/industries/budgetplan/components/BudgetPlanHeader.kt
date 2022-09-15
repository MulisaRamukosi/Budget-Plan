package com.puzzle.industries.budgetplan.components

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Money
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.components.text.SingleLineText
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@ExperimentalMaterial3Api
@Composable
fun BudgetPlanHeader(modifier: Modifier = Modifier) {
    Surface(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(vertical = MaterialTheme.spacing.large)) {

            Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)) {
                Title()
                Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.small))
                RemainingCash()
                Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.small))
                BalanceInfo()
            }

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.small))
            ChipOptions()
        }


    }
}

@Composable
private fun Title() {
    Text(
        text = stringResource(id = R.string.remaining_cash),
        style = MaterialTheme.typography.titleSmall
    )
}

@Composable
private fun RemainingCash() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.Money,
            contentDescription = stringResource(id = R.string.desc_money_icon)
        )
        Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.small))
        Text(
            text = stringResource(id = R.string.currency_amount, "R", 45000.0),
            style = MaterialTheme.typography.headlineLarge,
            color = MaterialTheme.colorScheme.primary
        )
    }
}

@Composable
private fun BalanceInfo() {
    Row(verticalAlignment = Alignment.CenterVertically) {
        MoneyInfo(
            modifier = Modifier,
            title = stringResource(id = R.string.total_income_for_month),
            amount = stringResource(id = R.string.currency_amount, "R", 64000.0)
        )
        Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))
        MoneyInfo(
            modifier = Modifier,
            title = stringResource(id = R.string.forecast_after_all_payments),
            amount = stringResource(id = R.string.currency_amount, "R", 64000.0)
        )
    }
}

@Composable
@ExperimentalMaterial3Api
private fun ChipOptions() {
    val chipRowScrollState = rememberScrollState()

    Row(
        modifier = Modifier
            .horizontalScroll(state = chipRowScrollState)
            .padding(horizontal = MaterialTheme.spacing.medium)

    ) {
        AssistChip(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Payments,
                    contentDescription = stringResource(id = R.string.desc_payment_icon)
                )
            },
            label = { Text(text = stringResource(id = R.string.view_expenses)) },
            onClick = {}
        )

        H_S_Space()

        AssistChip(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Event,
                    contentDescription = stringResource(id = R.string.desc_event_icon)
                )
            },
            label = { Text(text = stringResource(id = R.string.view_next_payments)) },
            onClick = {}
        )
    }
}

@Composable
private fun MoneyInfo(modifier: Modifier, title: String, amount: String) {
    Column(modifier = modifier) {

        SingleLineText(text = title, style = MaterialTheme.typography.labelSmall)

        Text(
            text = amount,
            style = MaterialTheme.typography.labelLarge,
            color = MaterialTheme.colorScheme.tertiary
        )
    }
}

@Preview
@Composable
@ExperimentalMaterial3Api
@ExperimentalMaterial3WindowSizeClassApi
fun PreviewBudgetPlanHeader() {
    BudgetPlanTheme(dynamicColor = false) {
        BudgetPlanHeader(modifier = Modifier.fillMaxWidth())
    }
}