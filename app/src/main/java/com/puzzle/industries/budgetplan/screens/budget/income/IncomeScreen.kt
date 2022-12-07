@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalFoundationApi::class)

package com.puzzle.industries.budgetplan.screens.budget.income

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.lazy.staggeredgrid.items
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Warning
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.dialog.ReasonPickerDialog
import com.puzzle.industries.budgetplan.components.income.IncomeItem
import com.puzzle.industries.budgetplan.components.income.TotalIncome
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.uiState.EmptyView
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.configs.ViewConfig
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import com.puzzle.industries.domain.models.DebtCheckResult
import com.puzzle.industries.domain.models.income.Income
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun IncomeScreen(
    incomeViewModel: IncomeViewModel,
    onEditItemClick: (Income) -> Unit,
    onAddIncomeClick: () -> Unit
) {

    SetupDeleteHandler(incomeViewModel = incomeViewModel)

    Box(modifier = Modifier.fillMaxSize()) {

        Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)) {
            V_M_Space()

            TotalIncomeField(incomeViewModel = incomeViewModel)

            V_M_Space()

            IncomeItems(
                modifier = Modifier.weight(weight = 1f),
                incomeViewModel = incomeViewModel,
                onEditItemClick = onEditItemClick
            )

        }

        Button(
            onClick = onAddIncomeClick,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(all = MaterialTheme.spacing.medium),
            content = { Text(text = stringResource(id = R.string.add_income)) }
        )
    }
}

@Composable
private fun IncomeItems(
    modifier: Modifier = Modifier,
    incomeViewModel: IncomeViewModel,
    onEditItemClick: (Income) -> Unit
) {
    val incomes by incomeViewModel.incomes.collectAsState()

    if (incomes.isEmpty()) {
        EmptyView(
            modifier = Modifier.fillMaxSize(),
            message = stringResource(id = R.string.empty_incomes)
        )
    } else {
        val currencySymbol by incomeViewModel.currencySymbol.collectAsState()

        LazyVerticalStaggeredGrid(
            modifier = modifier,
            contentPadding = PaddingValues(bottom = MaterialTheme.spacing.large * 2),
            verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium),
            columns = StaggeredGridCells.Adaptive(minSize = ViewConfig.staggeredGridItemMinWidth),
        ) {
            items(items = incomes) { income ->
                IncomeItem(
                    modifier = Modifier.fillMaxWidth(),
                    currencySymbol = currencySymbol,
                    income = income,
                    onEditClick = onEditItemClick,
                    onDeleteClick = incomeViewModel.onDeleteIncome
                )
            }
        }
    }
}

@Composable
private fun TotalIncomeField(incomeViewModel: IncomeViewModel) {
    val totalIncome by incomeViewModel.totalIncome.collectAsState()
    val currencySymbol by incomeViewModel.currencySymbol.collectAsState()

    TotalIncome(
        currencySymbol = currencySymbol,
        totalIncome = totalIncome
    )
}

@Composable
private fun SetupDeleteHandler(incomeViewModel: IncomeViewModel) {
    val deleteReasons = stringArrayResource(id = R.array.income_delete_reasons).toList()

    val showDeleteReasonDialog = rememberSaveable { mutableStateOf(false) }
    val showDebtWarningDialog = rememberSaveable { mutableStateOf(false) }

    val debtCheckResult = remember { mutableStateOf<DebtCheckResult?>(value = null) }
    val incomeToDelete = remember { MutableStateFlow<Income?>(value = null) }
    val deleteReason = remember { mutableStateOf(value = deleteReasons[0]) }

    val currencySymbol by incomeViewModel.currencySymbol.collectAsState()

    LaunchedEffect(key1 = true) {
        incomeViewModel.crudResponseEventListener.collectLatest {
            if (it.response) {
                showDeleteReasonDialog.value = false
                showDebtWarningDialog.value = false
            } else {
                //TODO: show error message
            }
        }
    }

    LaunchedEffect(key1 = true) {
        incomeViewModel.debtWarning.collectLatest {
            if (it.willBeInDebt) {
                debtCheckResult.value = it
                showDebtWarningDialog.value = true
            }
        }
    }

    LaunchedEffect(key1 = true) {
        incomeViewModel.deleteIncomeEventListener.collectLatest { income ->
            incomeToDelete.value = income
            showDeleteReasonDialog.value = true
        }
    }

    val deleteIncome: () -> Unit = {
        incomeToDelete.value?.let { income ->
            incomeViewModel.deleteIncome(reason = deleteReason.value, income)
        }
    }

    if (showDebtWarningDialog.value) {
        AlertDialog(
            icon = {
                Icon(
                    imageVector = Icons.Rounded.Warning,
                    contentDescription = stringResource(id = R.string.desc_warning)
                )
            },
            title = {
                Text(
                    text = stringResource(id = R.string.debt_warning),
                    style = MaterialTheme.typography.headlineSmall
                )
            },
            text = {
                debtCheckResult.value?.let {
                    Text(
                        text = stringResource(
                            id = R.string.income_debt_warning_message,
                            currencySymbol,
                            it.amount,
                            stringArrayResource(id = R.array.months)[it.forMonth.ordinal],
                            it.forYear
                        )
                    )
                }

            },
            onDismissRequest = { showDebtWarningDialog.value = false },
            dismissButton = {
                TextButton(onClick = {
                    showDebtWarningDialog.value = false
                    showDeleteReasonDialog.value = false
                }) {
                    Text(text = stringResource(id = R.string.cancel))
                }
            },
            confirmButton = {
               TextButton(onClick = { deleteIncome() }) {
                   Text(text = stringResource(id = R.string.delete_anyway))
               }
            }
        )
    }

    if (showDeleteReasonDialog.value) {
        ReasonPickerDialog(
            title = stringResource(id = R.string.delete_income),
            reasonSupportingText = stringResource(id = R.string.income_delete_supporting_text),
            dismissOnClickOutside = true,
            reasons = deleteReasons,
            preselectedReason = deleteReasons[0],
            positiveActionText = stringResource(id = R.string.delete),
            onPositiveActionClick = deleteIncome,
            onDoNotSpecifyClick = {
                deleteReason.value = ""
                deleteIncome()
            },
            onReasonChange = { deleteReason.value = it },
            onDismiss = { showDeleteReasonDialog.value = false }
        )
    }
}
