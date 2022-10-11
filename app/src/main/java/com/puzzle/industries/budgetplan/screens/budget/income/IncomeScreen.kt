@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.screens.budget.income

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
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
import com.puzzle.industries.budgetplan.components.income.IncomeItem
import com.puzzle.industries.budgetplan.components.income.TotalIncome
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import com.puzzle.industries.domain.models.income.Income
import kotlinx.coroutines.flow.MutableStateFlow

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
    val currencySymbol by incomeViewModel.currencySymbol.collectAsState()

    LazyColumn(
        modifier = modifier,
        contentPadding = PaddingValues(bottom = MaterialTheme.spacing.large * 2),
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium)
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
    val showDeleteReasonDialog = rememberSaveable { mutableStateOf(false) }

    val deleteReasons = stringArrayResource(id = R.array.income_delete_reasons).toList()

    val incomeToDelete = remember { MutableStateFlow<Income?>(value = null) }
    val deleteReason = remember { mutableStateOf(value = deleteReasons[0]) }

    LaunchedEffect(key1 = true) {
        incomeViewModel.crudResponseEventListener.collect {
            if (it.response) {
                showDeleteReasonDialog.value = false
            } else {
                //TODO: show error message
            }
        }
    }

    LaunchedEffect(key1 = true) {
        incomeViewModel.deleteIncomeEventListener.collect { income ->
            incomeToDelete.value = income
            showDeleteReasonDialog.value = true
        }
    }


    val deleteIncome: () -> Unit = {
        incomeToDelete.value?.let { income ->
            incomeViewModel.deleteIncome(reason = deleteReason.value, income)
        }

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
