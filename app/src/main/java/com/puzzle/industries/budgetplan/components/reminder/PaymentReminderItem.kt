@file:OptIn(
    ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3WindowSizeClassApi::class
)

package com.puzzle.industries.budgetplan.components.reminder

import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.MiniCaption
import com.puzzle.industries.budgetplan.components.ModifiableItemWrapper
import com.puzzle.industries.budgetplan.components.spacer.H_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_XS_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun PaymentReminderItem(
    modifier: Modifier,
    title: String,
    amount: Double,
    paymentDate: String,
    reminderFrequency: String
) {
    ModifiableItemWrapper(modifier = modifier) {
        Column(modifier = it) {

            SubtitleAndTitle(title)

            V_M_Space()

            Amount(amount)

            V_M_Space()

            Row {
                MiniCaption(imageVector = Icons.Rounded.Alarm, message = paymentDate)

                H_M_Space()

                MiniCaption(imageVector = Icons.Rounded.Repeat, message = reminderFrequency)
            }

        }
    }
}

@Composable
private fun Amount(amount: Double) {
    Text(
        text = stringResource(id = R.string.currency_amount, "R", amount),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun SubtitleAndTitle(title: String) {
    Text(
        text = stringResource(id = R.string.reminder_for_payment),
        style = MaterialTheme.typography.labelSmall
    )

    V_XS_Space()

    Text(
        text = title,
        maxLines = 1,
        overflow = TextOverflow.Ellipsis,
        style = MaterialTheme.typography.titleMedium
    )
}

@Preview
@Composable
fun PreviewPaymentReminderItem() {
    BudgetPlanTheme(dynamicColor = false) {
        PaymentReminderItem(
            modifier = Modifier.fillMaxWidth(),
            title = "Groceries",
            amount = 300.0,
            paymentDate = "on 2 Sep 2022",
            reminderFrequency = "once off"
        )
    }
}