package com.puzzle.industries.budgetplan.components.reminder

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.HomeCardTitle
import com.puzzle.industries.budgetplan.components.SingleLineText
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun PaymentReminders(modifier: Modifier) {
    Card(modifier = modifier) {

        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.medium)) {

            HomeCardTitle(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.payment_reminders)
            )
            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))
            AlarmItems()

        }
    }
}

@Composable
private fun AlarmItems() {
    AlarmItem(modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium))
    AlarmItem(modifier = Modifier.padding(bottom = MaterialTheme.spacing.medium))
    AlarmItem()
}


@Composable
private fun AlarmItem(modifier: Modifier = Modifier) {
    Row(modifier = modifier) {
        Icon(
            imageVector = Icons.Rounded.Alarm,
            contentDescription = stringResource(id = R.string.desc_alarm_icon),
            tint = MaterialTheme.colorScheme.tertiary
        )

        Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))

        Column {
            SingleLineText(text = "Investment", style = MaterialTheme.typography.bodyMedium)
            SingleLineText(
                text = "payment on 3 sep 2022",
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))

        SingleLineText(
            text = stringResource(id = R.string.currency_amount, "R", 1000.0),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )

    }
}

@Preview
@Composable
@ExperimentalMaterial3WindowSizeClassApi
fun PreviewPaymentReminders() {
    BudgetPlanTheme(dynamicColor = false) {
        PaymentReminders(modifier = Modifier)
    }
}