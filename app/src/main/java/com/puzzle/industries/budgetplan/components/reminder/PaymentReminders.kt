@file:OptIn(ExperimentalAnimationApi::class)

package com.puzzle.industries.budgetplan.components.reminder

import androidx.compose.animation.*
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.AlarmOff
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.components.text.HomeCardTitle
import com.puzzle.industries.budgetplan.components.text.SingleLineText
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.configs.ViewConfig
import com.puzzle.industries.budgetplan.viewModels.budget.reminder.ReminderViewModel
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense

@Composable
fun PaymentReminders(modifier: Modifier, reminderViewModel: ReminderViewModel) {
    val reminders by reminderViewModel.reminders.collectAsState()
    val currencySymbol by reminderViewModel.currencySymbol.collectAsState()

    Card(modifier = modifier) {

        Column(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium)
                .padding(top = MaterialTheme.spacing.medium)
        ) {

            HomeCardTitle(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = stringResource(id = R.string.payment_reminders)
            )

            V_M_Space()

            if (reminders.isNotEmpty()) {
                AlarmItems(currencySymbol = currencySymbol, reminders = reminders)
            } else {
                NoAlarmsAvailable(modifier = Modifier.fillMaxWidth())
            }
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
            text = stringResource(id = R.string.no_payment_reminders_available),
            color = MaterialTheme.colorScheme.secondary,
            style = MaterialTheme.typography.bodyMedium
        )

        V_M_Space()
    }
}

@Composable
private fun AlarmItems(currencySymbol: String, reminders: List<ReminderWithExpense>) {
    var showAllAlarms by rememberSaveable {
        mutableStateOf(false)
    }

    Column {

        Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)) {
            reminders.take(3).forEach { reminder ->
                AlarmItem(currencySymbol = currencySymbol, reminder = reminder)
            }

            AnimatedVisibility(
                visible = showAllAlarms,
                enter = slideInVertically(initialOffsetY = { -40 })
                        + expandVertically(expandFrom = Alignment.Top)
                        + scaleIn(transformOrigin = TransformOrigin(0.5f, 0f))
                        + fadeIn(initialAlpha = 0.3f),
                exit = slideOutVertically()
                        + shrinkVertically()
                        + fadeOut()
                        + scaleOut(targetScale = 1.2f)
            ) {
                Column(verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.medium)) {
                    for (i in ViewConfig.maxReminderItemsToDisplayInHome until reminders.size) {
                        AlarmItem(currencySymbol = currencySymbol, reminder = reminders[i])
                    }
                }
            }
        }

        if (reminders.size > ViewConfig.maxReminderItemsToDisplayInHome) {
            ViewMoreReminders(showAllAlarms = showAllAlarms) {
                showAllAlarms = it
            }
        } else {
            V_M_Space()
        }
    }
}

@Composable
private fun ViewMoreReminders(showAllAlarms: Boolean, onOptionChange: (Boolean) -> Unit) {
    TextButton(
        modifier = Modifier.fillMaxWidth(),
        onClick = { onOptionChange(!showAllAlarms) }
    ) {
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Text(
                modifier = Modifier.weight(weight = 1f),
                text = stringResource(
                    id = if (showAllAlarms) R.string.view_less_reminders
                    else R.string.view_more_reminders
                ),
                color = MaterialTheme.colorScheme.tertiary,
                style = MaterialTheme.typography.bodySmall
            )

            Icon(
                modifier = Modifier.rotate(degrees = if (showAllAlarms) 180f else 0f),
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = stringResource(id = R.string.desc_arrow_drop_down_icon),
                tint = MaterialTheme.colorScheme.tertiary
            )
        }
    }
}

@Composable
private fun AlarmItem(
    modifier: Modifier = Modifier,
    currencySymbol: String,
    reminder: ReminderWithExpense
) {
    val expense = reminder.expense

    Row(modifier = modifier) {
        Icon(
            imageVector = Icons.Rounded.Alarm,
            contentDescription = stringResource(id = R.string.desc_alarm_icon),
            tint = MaterialTheme.colorScheme.tertiary
        )

        H_M_Space()

        Column(modifier = Modifier.weight(weight = 1f)) {
            SingleLineText(text = expense.name, style = MaterialTheme.typography.bodyMedium)
            SingleLineText(
                text = constructPaymentReminderMessage(
                    frequencyType = expense.frequencyType,
                    frequencyWhen = expense.frequencyWhen
                ),
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.labelSmall
            )
        }

        Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))

        SingleLineText(
            text = stringResource(id = R.string.currency_amount, currencySymbol, expense.amount),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.primary
        )

    }
}

@Composable
private fun constructPaymentReminderMessage(
    frequencyType: FrequencyType,
    frequencyWhen: String
): String {
    return when (frequencyType) {
        FrequencyType.ONCE_OFF -> stringResource(id = R.string.once_off_payment, frequencyWhen)
        FrequencyType.WEEKLY -> stringResource(
            id = R.string.weekly_payment_on,
            frequencyWhen.lowercase()
        )
        FrequencyType.DAILY -> stringResource(id = R.string.daily_payment)
        else -> ""
    }
}

@Preview
@Composable
@ExperimentalMaterial3WindowSizeClassApi
fun PreviewPaymentReminders() {
    BudgetPlanTheme(dynamicColor = false) {
        PaymentReminders(modifier = Modifier, reminderViewModel = hiltViewModel())
    }
}