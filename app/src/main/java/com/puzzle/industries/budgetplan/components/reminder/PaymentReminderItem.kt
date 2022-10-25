package com.puzzle.industries.budgetplan.components.reminder

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material.icons.rounded.Repeat
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.SwitchButton
import com.puzzle.industries.budgetplan.components.layout.MiniCaption
import com.puzzle.industries.budgetplan.components.layout.ModifiableItemWrapper
import com.puzzle.industries.budgetplan.components.spacer.H_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_XS_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.reminder.Reminder
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense
import java.util.*

@Composable
fun PaymentReminderItem(
    modifier: Modifier,
    reminderWithExpense: ReminderWithExpense,
    currencySymbol: String,
    onDelete: (Reminder) -> Unit,
    onRemindChange: (Boolean) -> Unit
) {
    val expense = reminderWithExpense.expense
    val reminder = reminderWithExpense.reminder

    ModifiableItemWrapper(modifier = modifier, onDeleteClick = { onDelete(reminder) }) {
        Column(
            modifier = Modifier
                .padding(all = MaterialTheme.spacing.medium)
                .fillMaxWidth()
        ) {

            Row {

                SwitchButton(checked = reminder.remind, onCheckChanged = onRemindChange) {
                    SubtitleAndTitle(title = expense.name)
                }
            }


            V_M_Space()

            Amount(amount = expense.amount, currencySymbol = currencySymbol)

            V_M_Space()

            Row {
                if (expense.frequencyType != FrequencyType.DAILY) {
                    MiniCaption(imageVector = Icons.Rounded.Alarm, message = expense.frequencyWhen)

                    H_M_Space()
                }

                MiniCaption(
                    imageVector = Icons.Rounded.Repeat,
                    message = expense.frequencyType.name.lowercase()
                )
            }

        }
    }
}

@Composable
private fun Amount(amount: Double, currencySymbol: String) {
    Text(
        text = stringResource(id = R.string.currency_amount, currencySymbol, amount),
        color = MaterialTheme.colorScheme.primary,
        style = MaterialTheme.typography.headlineSmall
    )
}

@Composable
private fun SubtitleAndTitle(title: String) {
    Column {
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
}

@Preview
@Composable
fun PreviewPaymentReminderItem() {
    BudgetPlanTheme(dynamicColor = false) {
        PaymentReminderItem(
            modifier = Modifier.fillMaxWidth(),
            reminderWithExpense = ReminderWithExpense(
                reminder = Reminder(id = 1, expenseId = UUID.randomUUID(), remind = true),
                expense = Expense(
                    expenseGroupId = UUID.randomUUID(),
                    name = "Groceries",
                    amount = 300.0,
                    frequencyWhen = "on 2 Sep 2022",
                    frequencyType = FrequencyType.ONCE_OFF,
                    description = "some desc"
                )
            ),
            currencySymbol = "$",
            onDelete = {},
            onRemindChange = {}
        )
    }
}