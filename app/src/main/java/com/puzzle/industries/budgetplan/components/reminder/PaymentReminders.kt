package com.puzzle.industries.budgetplan.components.reminder

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.AlarmOff
import androidx.compose.material3.Card
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.components.text.HomeCardTitle
import com.puzzle.industries.budgetplan.components.text.SingleLineText
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

            V_M_Space()

            AlarmItems()
            //NoAlarmsAvailable()
        }
    }
}

@Composable
private fun NoAlarmsAvailable(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        Icon(
            imageVector = Icons.Rounded.AlarmOff,
            contentDescription = stringResource(id = R.string.desc_check_alarm_off_icon),
            tint = MaterialTheme.colorScheme.primary,
            modifier = Modifier.size(size = 64.dp)
        )

        V_S_Space()

        Text(
            text = stringResource(id = R.string.no_alarms_available),
            color = MaterialTheme.colorScheme.secondary
        )
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

        H_M_Space()

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