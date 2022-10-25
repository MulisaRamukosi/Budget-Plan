@file:OptIn(ExperimentalFoundationApi::class)

package com.puzzle.industries.budgetplan.screens.budget.reminder

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.expenses.ExpenseItem
import com.puzzle.industries.budgetplan.components.layout.OrientationAwareContentWrapper
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.layout.buildOrientationAwareActions
import com.puzzle.industries.budgetplan.viewModels.budget.reminder.AddReminderViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.reminder.ReminderViewModel

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

@Composable
private fun SetUpCrudResponseEventListener(
    reminderViewModel: ReminderViewModel,
    dismiss: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        reminderViewModel.crudResponseEventListener.collect {
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