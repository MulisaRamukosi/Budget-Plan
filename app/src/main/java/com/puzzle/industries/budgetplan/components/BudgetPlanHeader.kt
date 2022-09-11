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
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@ExperimentalMaterial3Api
@Composable
fun BudgetPlanHeader(modifier: Modifier) {
    val chipRowScrollState = rememberScrollState()

    Surface(
        modifier = modifier
    ) {
        Column(modifier = Modifier.padding(vertical = MaterialTheme.spacing.large)) {
            Column(
                modifier = Modifier.padding(
                    horizontal = MaterialTheme.spacing.medium
                )
            ) {
                Text(
                    text = stringResource(id = R.string.remaining_cash),
                    style = MaterialTheme.typography.titleSmall
                )

                Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.small))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    Icon(
                        imageVector = Icons.Rounded.Money,
                        contentDescription = stringResource(id = R.string.desc_money_icon)
                    )
                    Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.small))
                    Text(
                        text = stringResource(id = R.string.total_amount, "R", 45000.0),
                        style = MaterialTheme.typography.headlineLarge,
                        color = MaterialTheme.colorScheme.primary
                    )
                }

                Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.small))

                Row(verticalAlignment = Alignment.CenterVertically) {
                    MoneyInfo(
                        modifier = Modifier,
                        title = stringResource(id = R.string.total_income_for_month),
                        amount = stringResource(id = R.string.total_amount, "R", 64000.0)
                    )
                    Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))
                    MoneyInfo(
                        modifier = Modifier,
                        title = stringResource(id = R.string.forecast_after_all_payments),
                        amount = stringResource(id = R.string.total_amount, "R", 64000.0)
                    )
                }
            }

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.small))

            Row(
                modifier = Modifier
                    .horizontalScroll(
                        state = chipRowScrollState
                    )
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

                Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.small))

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


    }
}

@Composable
private fun MoneyInfo(modifier: Modifier, title: String, amount: String) {
    Column(modifier = modifier) {
        Text(
            text = title,
            style = MaterialTheme.typography.labelSmall,
            overflow = TextOverflow.Ellipsis,
            maxLines = 1
        )
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