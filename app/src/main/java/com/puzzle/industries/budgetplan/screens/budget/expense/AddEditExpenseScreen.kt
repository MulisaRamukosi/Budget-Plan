package com.puzzle.industries.budgetplan.screens.budget.expense

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.dialog.ReasonPickerDialog
import com.puzzle.industries.budgetplan.components.inputs.AmountInput
import com.puzzle.industries.budgetplan.components.inputs.DescriptionInput
import com.puzzle.industries.budgetplan.components.inputs.TitleInput
import com.puzzle.industries.budgetplan.components.layout.OrientationAwareContentWrapper
import com.puzzle.industries.budgetplan.components.picker.FrequencyTypePicker
import com.puzzle.industries.budgetplan.components.picker.FrequencyWhenPicker
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.factory.FrequencyDateFactory
import com.puzzle.industries.budgetplan.util.configs.FrequencyConfig
import com.puzzle.industries.budgetplan.util.layout.buildOrientationAwareActions
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.AddEditExpenseViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.ExpenseViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.FrequencyDate

@Composable
fun AddEditExpenseScreen(
    expenseViewModel: ExpenseViewModel,
    incomeViewModel: IncomeViewModel,
    addEditExpenseViewModel: AddEditExpenseViewModel,
    isInTabletLandscape: Boolean,
    onNavigateBackToParent: () -> Unit
) {
    SetUpCrudActionDefaultReason(addEditExpenseViewModel = addEditExpenseViewModel)

    SetUpEventListener(
        expenseViewModel = expenseViewModel,
        addEditExpenseViewModel = addEditExpenseViewModel,
        dismiss = onNavigateBackToParent
    )

    OrientationAwareContentWrapper(
        title = stringResource(id = R.string.expense),
        subTitle = stringResource(
            id = if (addEditExpenseViewModel.isUpdatingConditionHandler.getValue()) R.string.update_expense_subtitle
            else R.string.add_expense_subtitle
        ),
        onDismiss = onNavigateBackToParent,
        isInTabletLandscape = isInTabletLandscape,
        actions = getOrientationActions(
            expenseViewModel = expenseViewModel,
            addEditExpenseViewModel = addEditExpenseViewModel,
            isInLandscape = isInTabletLandscape,
            onDismiss = onNavigateBackToParent
        )
    ) { paddingValues ->
        Column {
            val horizontalPadding = remember {
                paddingValues.calculateStartPadding(layoutDirection = LayoutDirection.Ltr)
            }

            Column(modifier = Modifier.padding(paddingValues = paddingValues)) {
                TitleTextField(addEditExpenseViewModel = addEditExpenseViewModel)
                V_M_Space()

                RemainingAmount(
                    addEditExpenseViewModel = addEditExpenseViewModel,
                    expenseViewModel = expenseViewModel,
                    incomeViewModel = incomeViewModel
                )

                AmountTextField(addEditExpenseViewModel = addEditExpenseViewModel)

                V_M_Space()

                DescriptionTextField(addEditExpenseViewModel = addEditExpenseViewModel)


                V_M_Space()

                Text(
                    text = stringResource(id = R.string.expense_frequency),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = stringResource(id = R.string.note_expense_frequency),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            FrequencyTypePickerField(
                addEditExpenseViewModel = addEditExpenseViewModel,
                horizontalPadding = horizontalPadding.value.dp
            )
        }
    }

}

@Composable
private fun SetUpCrudActionDefaultReason(addEditExpenseViewModel: AddEditExpenseViewModel) {
    if (!addEditExpenseViewModel.isUpdatingConditionHandler.getValue()) {
        val defaultReason = stringResource(id = R.string.initial)
        addEditExpenseViewModel.crudActionReasonHandler.onValueChange(defaultReason)
    } else if (addEditExpenseViewModel.crudActionReasonHandler.getValue().isBlank()) {
        val updateReasons = stringArrayResource(id = R.array.expense_update_reasons).toList()
        val defaultUpdateReason = updateReasons[0]
        addEditExpenseViewModel.crudActionReasonHandler.onValueChange(defaultUpdateReason)
    }
}

@Composable
private fun SetUpEventListener(
    expenseViewModel: ExpenseViewModel,
    addEditExpenseViewModel: AddEditExpenseViewModel,
    dismiss: () -> Unit
) {
    SetUpCrudResponseEventListener(
        expenseViewModel = expenseViewModel,
        dismiss = dismiss
    )

    SetUpUpdateEventListener(
        expenseViewModel = expenseViewModel,
        addEditExpenseViewModel = addEditExpenseViewModel
    )

}

@Composable
private fun SetUpCrudResponseEventListener(
    expenseViewModel: ExpenseViewModel,
    dismiss: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        expenseViewModel.crudResponseEventListener.collect {
            if (it.response) {
                dismiss()
            } else {
                //TODO: show error message
            }
        }
    }
}

@Composable
private fun SetUpUpdateEventListener(
    expenseViewModel: ExpenseViewModel,
    addEditExpenseViewModel: AddEditExpenseViewModel
) {
    val showUpdateReasonDialog = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        expenseViewModel.updateExpenseEventListener.collect {
            showUpdateReasonDialog.value = true
        }
    }

    val updateExpense: () -> Unit = {
        expenseViewModel.updateExpenses(
            reason = addEditExpenseViewModel.crudActionReasonHandler.getValue(),
            addEditExpenseViewModel.expense
        )
    }

    if (showUpdateReasonDialog.value) {
        ReasonPickerDialog(
            title = stringResource(id = R.string.update_expense),
            reasonSupportingText = stringResource(id = R.string.expense_update_supporting_text),
            reasons = stringArrayResource(id = R.array.income_update_reasons).toList(),
            preselectedReason = addEditExpenseViewModel.crudActionReasonHandler.getValue(),
            positiveActionText = stringResource(id = R.string.update),
            onPositiveActionClick = updateExpense,
            onDoNotSpecifyClick = {
                addEditExpenseViewModel.crudActionReasonHandler.onValueChange("")
                updateExpense()
            },
            onReasonChange = addEditExpenseViewModel.crudActionReasonHandler.onValueChange,
            onDismiss = { showUpdateReasonDialog.value = false }
        )
    }
}

@Composable
private fun TitleTextField(addEditExpenseViewModel: AddEditExpenseViewModel) {
    val title by addEditExpenseViewModel.titleStateFlowHandler.valueStateFlow.collectAsState()

    TitleInput(
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Next,
        title = title,
        onValueChange = addEditExpenseViewModel.titleStateFlowHandler.onValueChange
    )
}

@Composable
private fun AmountTextField(addEditExpenseViewModel: AddEditExpenseViewModel) {
    val currencySymbol by addEditExpenseViewModel.currencySymbol.collectAsState()

    AmountInput(
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Next,
        income = addEditExpenseViewModel.amountStateFlowHandler.getValue(),
        onValueChange = addEditExpenseViewModel.amountStateFlowHandler.onValueChange,
        currencySymbol = currencySymbol
    )
}

@Composable
private fun DescriptionTextField(addEditExpenseViewModel: AddEditExpenseViewModel) {
    val description by addEditExpenseViewModel.descriptionStateFlowHandler.valueStateFlow.collectAsState()

    DescriptionInput(
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Done,
        description = description,
        onValueChange = addEditExpenseViewModel.descriptionStateFlowHandler.onValueChange
    )
}

@Composable
private fun getOrientationActions(
    expenseViewModel: ExpenseViewModel,
    addEditExpenseViewModel: AddEditExpenseViewModel,
    isInLandscape: Boolean,
    onDismiss: () -> Unit
): @Composable RowScope.() -> Unit {
    val isUpdating = addEditExpenseViewModel.isUpdatingConditionHandler.getValue()
    val saveUpdateText: String =
        stringResource(id = if (isUpdating) R.string.update_expense else R.string.save_expense)

    val saveOrUpdateExpenseClick: () -> Unit = {
        if (isUpdating) {
            expenseViewModel.emitUpdateExpenseEvent()
        } else {
            expenseViewModel.saveExpenses(
                reason = addEditExpenseViewModel.crudActionReasonHandler.getValue(),
                addEditExpenseViewModel.expense
            )
        }
    }

    val allInputsEntered by addEditExpenseViewModel.requiredInputsStateFlowHandler.valueStateFlow.collectAsState()

    return buildOrientationAwareActions(
        isInLandscape = isInLandscape,
        saveUpdateText = saveUpdateText,
        allInputsEntered = allInputsEntered,
        onSaveOrUpdateClickListener = saveOrUpdateExpenseClick,
        onDismiss = onDismiss
    )
}

@Composable
private fun RemainingAmount(
    addEditExpenseViewModel: AddEditExpenseViewModel,
    expenseViewModel: ExpenseViewModel,
    incomeViewModel: IncomeViewModel
) {
    val enteredAmount by addEditExpenseViewModel.amountStateFlowHandler.valueStateFlow.collectAsState()
    val frequencyType by addEditExpenseViewModel.frequencyTypeStateFlowHandler.valueStateFlow.collectAsState()
    val frequencyWhen by addEditExpenseViewModel.frequencyWhenStateFlowHandler.valueStateFlow.collectAsState()
    val currencySymbol by expenseViewModel.currencySymbol.collectAsState()

    if (enteredAmount > 0) {
        val totalIncome by incomeViewModel.totalIncomeForMonth.collectAsState()
        val totalExpense by expenseViewModel.totalExpenseForMonth.collectAsState()

        if(frequencyType == FrequencyType.ONCE_OFF) {
            val frequencyDate = FrequencyDate.parseDate(date = frequencyWhen)
            expenseViewModel.getTotalExpensesForMonth(month = frequencyDate.month)
            incomeViewModel.getTotalIncomesForMonth(month = frequencyDate.month)
        }

        Text(
            text = stringResource(
                id = R.string.expense_remaining_amount,
                currencySymbol,
                totalIncome - totalExpense - enteredAmount
            ),
            style = MaterialTheme.typography.bodySmall,
            color = MaterialTheme.colorScheme.secondary
        )
    }

}

@Composable
private fun FrequencyTypePickerField(
    addEditExpenseViewModel: AddEditExpenseViewModel,
    horizontalPadding: Dp
) {
    val selectedFrequency by addEditExpenseViewModel.frequencyTypeStateFlowHandler.valueStateFlow.collectAsState()
    val selectedFrequencyNote = stringResource(
        id = when (selectedFrequency) {
            FrequencyType.ONCE_OFF -> R.string.note_select_expense_date
            FrequencyType.DAILY -> R.string.empty
            FrequencyType.WEEKLY -> R.string.note_select_expense_week_day
            FrequencyType.MONTHLY -> R.string.note_select_expense_month_day
            FrequencyType.YEARLY -> R.string.note_select_yearly_expense_date
        }
    )

    FrequencyTypePicker(
        selectedFrequency = selectedFrequency,
        horizontalPadding = horizontalPadding.value.dp,
        onFrequencySelected = { type ->
            addEditExpenseViewModel.frequencyWhenStateFlowHandler.onValueChange(
                when (type) {
                    FrequencyType.ONCE_OFF -> FrequencyDateFactory.createCurrentDate()
                        .toString()
                    FrequencyType.MONTHLY -> FrequencyConfig.DEFAULT_WHEN
                    FrequencyType.DAILY -> ""
                    FrequencyType.WEEKLY -> WeekDays.MONDAY.name
                    FrequencyType.YEARLY -> FrequencyDateFactory.createCurrentDate()
                        .formatToDayMonth()
                }
            )
            addEditExpenseViewModel.frequencyTypeStateFlowHandler.onValueChange(type)
        }
    )

    FrequencyWhenPicker(
        selectedFrequency = selectedFrequency,
        selectedValue = addEditExpenseViewModel.frequencyWhenStateFlowHandler.getValue(),
        frequencyNote = selectedFrequencyNote,
        horizontalPadding = horizontalPadding.value.dp,
        onValueChange = addEditExpenseViewModel.frequencyWhenStateFlowHandler.onValueChange
    )
}