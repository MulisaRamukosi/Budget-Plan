@file:OptIn(ExperimentalFoundationApi::class)

package com.puzzle.industries.budgetplan.screens.budget.expense

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.dialog.ReasonPickerDialog
import com.puzzle.industries.budgetplan.components.expenses.ExpenseGroupItem
import com.puzzle.industries.budgetplan.components.income.TotalIncome
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.configs.ViewConfig
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.ExpenseViewModel
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses
import kotlinx.coroutines.flow.MutableStateFlow

@Composable
fun ExpenseScreen(
    expenseViewModel: ExpenseViewModel,
    onEditExpenseGroup: (ExpenseGroup) -> Unit,
    onEditExpense: (Expense) -> Unit,
    onAddExpenseGroup: () -> Unit,
    onAddExpense: (ExpenseGroup) -> Unit
) {

    SetupDeleteHandler(expenseViewModel = expenseViewModel)

    Box(modifier = Modifier.fillMaxWidth()) {
        Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)) {
            V_M_Space()

            TotalIncomeField(expenseViewModel = expenseViewModel)

            V_M_Space()

            ExpenseGroupWithExpensesItems(
                modifier = Modifier.weight(weight = 1f),
                expenseViewModel = expenseViewModel,
                onEditExpenseGroup = onEditExpenseGroup,
                onDeleteExpenseGroupWithExpenses = expenseViewModel.onDeleteExpenseGroupWithExpenses,
                onEditExpense = onEditExpense,
                onDeleteExpense = expenseViewModel.onDeleteExpense,
                onAddExpense = onAddExpense
            )
        }

        Button(
            onClick = onAddExpenseGroup,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(all = MaterialTheme.spacing.medium),
            content = { Text(text = stringResource(id = R.string.add_expense_category)) }
        )
    }
}

@Composable
private fun SetupDeleteHandler(expenseViewModel: ExpenseViewModel) {

    val showDeleteExpenseGroupDialog = rememberSaveable { mutableStateOf(false) }
    DeleteExpenseGroupHandler(
        expenseViewModel = expenseViewModel,
        showDialog = showDeleteExpenseGroupDialog.value,
        onShowDialog = {
            showDeleteExpenseGroupDialog.value = it
        }
    )


    val showDeleteExpenseDialog = rememberSaveable { mutableStateOf(false) }
    DeleteExpenseHandler(
        expenseViewModel = expenseViewModel,
        showDialog = showDeleteExpenseDialog.value,
        onShowDialog = {
            showDeleteExpenseDialog.value = it
        }
    )


    LaunchedEffect(key1 = true) {
        expenseViewModel.crudResponseEventListener.collect {
            if (it.response) {
                showDeleteExpenseDialog.value = false
                showDeleteExpenseGroupDialog.value = false
            } else {
                //TODO: show error message
            }
        }
    }
}

@Composable
private fun DeleteExpenseGroupHandler(
    expenseViewModel: ExpenseViewModel,
    showDialog: Boolean,
    onShowDialog: (Boolean) -> Unit
) {

    val deleteExpenseGroupWithExpensesReasons =
        stringArrayResource(id = R.array.expense_group_delete_reasons).toList()
    val deleteExpenseGroupReason =
        remember { mutableStateOf(value = deleteExpenseGroupWithExpensesReasons[0]) }

    val expenseGroupWithExpensesToDelete =
        remember { MutableStateFlow<ExpenseGroupWithExpenses?>(value = null) }

    LaunchedEffect(key1 = true) {
        expenseViewModel.deleteExpenseGroupWithExpensesEventListener.collect { expenseGroupWithExpenses ->
            expenseGroupWithExpensesToDelete.value = expenseGroupWithExpenses
            onShowDialog(true)
        }
    }

    val deleteExpenseGroup: () -> Unit = {
        expenseGroupWithExpensesToDelete.value?.let { expenseGroupWithExpenses ->
            expenseViewModel.deleteExpenseGroupWithExpenses(
                reason = deleteExpenseGroupReason.value,
                expenseGroupWithExpenses = expenseGroupWithExpenses
            )
        }
    }

    if (showDialog) {
        ReasonPickerDialog(
            title = stringResource(id = R.string.delete_expense_category),
            reasonSupportingText = stringResource(id = R.string.expense_category_delete_supporting_text),
            dismissOnClickOutside = true,
            reasons = deleteExpenseGroupWithExpensesReasons,
            preselectedReason = deleteExpenseGroupWithExpensesReasons[0],
            positiveActionText = stringResource(id = R.string.delete),
            onPositiveActionClick = deleteExpenseGroup,
            onDoNotSpecifyClick = {
                deleteExpenseGroupReason.value = ""
                deleteExpenseGroup()
            },
            onReasonChange = { deleteExpenseGroupReason.value = it },
            onDismiss = { onShowDialog(false) }
        )
    }
}

@Composable
private fun DeleteExpenseHandler(
    expenseViewModel: ExpenseViewModel,
    showDialog: Boolean,
    onShowDialog: (Boolean) -> Unit
) {
    val deleteExpenseReasons =
        stringArrayResource(id = R.array.expense_group_delete_reasons).toList()
    val deleteExpenseReason =
        remember { mutableStateOf(value = deleteExpenseReasons[0]) }

    val expenseToDelete = remember { MutableStateFlow<Expense?>(value = null) }

    LaunchedEffect(key1 = true) {
        expenseViewModel.deleteExpenseEventListener.collect { expense ->
            expenseToDelete.value = expense
            onShowDialog(true)
        }
    }

    val deleteExpense: () -> Unit = {
        expenseToDelete.value?.let { expense ->
            expenseViewModel.deleteExpenses(
                reason = deleteExpenseReason.value,
                expense
            )
        }
    }

    if (showDialog) {
        ReasonPickerDialog(
            title = stringResource(id = R.string.delete_expense),
            reasonSupportingText = stringResource(id = R.string.expense_delete_supporting_text),
            dismissOnClickOutside = true,
            reasons = deleteExpenseReasons,
            preselectedReason = deleteExpenseReasons[0],
            positiveActionText = stringResource(id = R.string.delete),
            onPositiveActionClick = deleteExpense,
            onDoNotSpecifyClick = {
                deleteExpenseReason.value = ""
                deleteExpense()
            },
            onReasonChange = { deleteExpenseReason.value = it },
            onDismiss = { onShowDialog(false) }
        )
    }
}

@Composable
private fun ExpenseGroupWithExpensesItems(
    modifier: Modifier,
    expenseViewModel: ExpenseViewModel,
    onEditExpenseGroup: (ExpenseGroup) -> Unit,
    onDeleteExpenseGroupWithExpenses: (ExpenseGroupWithExpenses) -> Unit,
    onEditExpense: (Expense) -> Unit,
    onDeleteExpense: (Expense) -> Unit,
    onAddExpense: (ExpenseGroup) -> Unit
) {
    val expenseGroupsWithExpenses by expenseViewModel.expenseGroupsWithExpenses.collectAsState()
    val currencySymbol by expenseViewModel.currencySymbol.collectAsState()

    LazyVerticalStaggeredGrid(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = MaterialTheme.spacing.large * 2),
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium),
        columns = StaggeredGridCells.Adaptive(minSize = ViewConfig.staggeredGridItemMinWidth)
    ){
        items(items = expenseGroupsWithExpenses) { expenseGroupWithExpenses ->
            ExpenseGroupItem(
                currencySymbol = currencySymbol,
                expenseGroupWithExpenses = expenseGroupWithExpenses,
                onAddExpenseClick = { onAddExpense(expenseGroupWithExpenses.expenseGroup) },
                onEditExpenseGroupClick = onEditExpenseGroup,
                onDeleteExpenseGroupClick = onDeleteExpenseGroupWithExpenses,
                onEditExpenseClick = onEditExpense,
                onDeleteExpenseClick = onDeleteExpense
            )
        }
    }
}

@Composable
private fun TotalIncomeField(expenseViewModel: ExpenseViewModel) {
    val totalExpenses by expenseViewModel.totalExpenses.collectAsState()
    val currencySymbol by expenseViewModel.currencySymbol.collectAsState()

    TotalIncome(
        currencySymbol = currencySymbol,
        totalIncome = totalExpenses
    )
}