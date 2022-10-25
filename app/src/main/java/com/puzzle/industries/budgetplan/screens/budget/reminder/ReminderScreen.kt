@file:OptIn(ExperimentalFoundationApi::class)

package com.puzzle.industries.budgetplan.screens.budget.reminder

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.reminder.PaymentReminderItem
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.configs.ViewConfig
import com.puzzle.industries.budgetplan.viewModels.budget.reminder.ReminderViewModel
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense

@Composable
fun ReminderScreens(reminderViewModel: ReminderViewModel, onAddReminder: () -> Unit) {
    Box(modifier = Modifier.fillMaxSize()) {

        ReminderItems(reminderViewModel = reminderViewModel)

        Button(
            onClick = onAddReminder,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(all = MaterialTheme.spacing.medium),
            content = { Text(text = stringResource(id = R.string.add_reminder)) }
        )
    }
}

@Composable
private fun ReminderItems(reminderViewModel: ReminderViewModel) {
    val reminderWithExpenses by reminderViewModel.reminders.collectAsState()
    val currencySymbol by reminderViewModel.currencySymbol.collectAsState()

    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(horizontal = MaterialTheme.spacing.medium)
            .padding(top = MaterialTheme.spacing.medium),
        contentPadding = PaddingValues(bottom = MaterialTheme.spacing.large * 2),
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium),
        columns = StaggeredGridCells.Adaptive(minSize = ViewConfig.staggeredGridItemMinWidth)
    ) {
        items(items = reminderWithExpenses) { reminderWithExpense: ReminderWithExpense ->
            PaymentReminderItem(
                modifier = Modifier.fillMaxWidth(),
                reminderWithExpense = reminderWithExpense,
                currencySymbol = currencySymbol,
                onDelete = {
                    reminderViewModel.deleteReminders(it)
                },
                onRemindChange = {
                    reminderWithExpense.reminder.remind = it
                    reminderViewModel.updateReminders(reminderWithExpense)
                }
            )
        }
    }

}