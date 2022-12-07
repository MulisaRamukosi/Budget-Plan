package com.puzzle.industries.budgetplan.screens.budget.reminder

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.expenses.ExpenseItem
import com.puzzle.industries.budgetplan.components.layout.OrientationAwareContentWrapper
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.layout.buildOrientationAwareActions
import com.puzzle.industries.budgetplan.viewModels.budget.reminder.AddReminderViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.reminder.ReminderViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddReminderScreen(
    reminderViewModel: ReminderViewModel,
    addReminderViewModel: AddReminderViewModel,
    isInTabletLandscape: Boolean,
    onNavigateBackToParent: () -> Unit
) {
    val expensesWithNoReminders by addReminderViewModel.expenses.collectAsState()
    val currencySymbol by addReminderViewModel.currencySymbol.collectAsState()

    SetUpCrudResponseEventListener(
        reminderViewModel = reminderViewModel,
        dismiss = onNavigateBackToParent
    )

    OrientationAwareContentWrapper(
        title = stringResource(id = R.string.payment_reminder),
        subTitle = stringResource(id = R.string.select_expenses_for_reminders),
        onDismiss = onNavigateBackToParent,
        isInTabletLandscape = isInTabletLandscape,
        actions = getOrientationActions(
            reminderViewModel = reminderViewModel,
            addReminderViewModel = addReminderViewModel,
            isInTabletLandscape = isInTabletLandscape,
            onDismiss = onNavigateBackToParent
        )
    ) {
        Column(
            modifier = Modifier.padding(paddingValues = it),
            verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium)
        ) {
            if (expensesWithNoReminders.isEmpty()) {
                NoExpensesAvailableView()
            } else {
                expensesWithNoReminders.forEach { expenseWithNoReminder ->
                    ExpenseItem(
                        modifier = Modifier.fillMaxWidth(),
                        currencySymbol = currencySymbol,
                        expense = expenseWithNoReminder,
                        isSelected = addReminderViewModel.isSelected(id = expenseWithNoReminder.id),
                        onSelect = { selected ->
                            addReminderViewModel.onSelectExpense(
                                id = expenseWithNoReminder.id,
                                selected = selected
                            )
                        }
                    )
                }
            }
        }
    }
}

@Composable
private fun SetUpCrudResponseEventListener(
    reminderViewModel: ReminderViewModel,
    dismiss: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        reminderViewModel.crudResponseEventListener.collectLatest {
            if (it.response) {
                dismiss()
            } else {
                //TODO: show error message
            }
        }
    }
}

@Composable
private fun getOrientationActions(
    reminderViewModel: ReminderViewModel,
    addReminderViewModel: AddReminderViewModel,
    isInTabletLandscape: Boolean,
    onDismiss: () -> Unit
): @Composable RowScope.() -> Unit {

    val setRemindersClick: () -> Unit = {
        reminderViewModel.saveReminders(
            expenses = addReminderViewModel.getSelectedExpenses().toTypedArray()
        )
    }

    val expensesSelected by addReminderViewModel.requiredInputsStateFlowHandler.valueStateFlow.collectAsState()

    return buildOrientationAwareActions(
        isInLandscape = isInTabletLandscape,
        saveUpdateText = stringResource(id = R.string.set_reminders),
        allInputsEntered = expensesSelected,
        onSaveOrUpdateClickListener = setRemindersClick,
        onDismiss = onDismiss
    )
}

@Composable
private fun NoExpensesAvailableView(modifier: Modifier = Modifier) {
    Column(modifier = modifier, horizontalAlignment = Alignment.CenterHorizontally) {
        V_M_Space()

        Image(
            modifier = Modifier.sizeIn(maxWidth = 100.dp, maxHeight = 100.dp),
            painter = painterResource(id = R.drawable.ic_no_data),
            contentDescription = stringResource(
                id = R.string.desc_no_data_icon
            )
        )

        V_M_Space()

        Text(
            text = stringResource(id = R.string.empty_expenses_for_reminders),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center
        )
    }
}