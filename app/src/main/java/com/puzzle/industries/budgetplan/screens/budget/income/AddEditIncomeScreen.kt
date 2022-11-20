package com.puzzle.industries.budgetplan.screens.budget.income

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
import com.puzzle.industries.budgetplan.components.picker.*
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.factory.FrequencyDateFactory
import com.puzzle.industries.budgetplan.util.configs.FrequencyConfig
import com.puzzle.industries.budgetplan.util.layout.buildOrientationAwareActions
import com.puzzle.industries.budgetplan.viewModels.budget.income.AddEditIncomeViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays

@Composable
fun AddEditIncomeScreen(
    incomeViewModel: IncomeViewModel,
    addEditIncomeViewModel: AddEditIncomeViewModel,
    isInTabletLandscape: Boolean,
    onNavigateBackToParent: () -> Unit
) {
    SetUpCrudActionDefaultReason(addEditIncomeViewModel = addEditIncomeViewModel)

    SetUpEventListener(
        incomeViewModel = incomeViewModel,
        addEditIncomeViewModel = addEditIncomeViewModel,
        dismiss = onNavigateBackToParent
    )

    OrientationAwareContentWrapper(
        title = stringResource(id = R.string.income),
        subTitle = stringResource(
            id = if (addEditIncomeViewModel.isUpdatingConditionHandler.getValue()) R.string.update_income_subtitle
            else R.string.add_income_subtitle
        ),
        onDismiss = onNavigateBackToParent,
        isInTabletLandscape = isInTabletLandscape,
        actions = getOrientationActions(
            incomeViewModel = incomeViewModel,
            addEditIncomeViewModel = addEditIncomeViewModel,
            isInLandscape = isInTabletLandscape,
            onDismiss = onNavigateBackToParent
        )
    ) { paddingValues ->
        Column {
            val horizontalPadding = remember {
                paddingValues.calculateStartPadding(layoutDirection = LayoutDirection.Ltr)
            }

            Column(modifier = Modifier.padding(paddingValues = paddingValues)) {
                TitleTextField(addEditIncomeViewModel = addEditIncomeViewModel)
                V_M_Space()

                AmountTextField(addEditIncomeViewModel = addEditIncomeViewModel)

                V_M_Space()

                DescriptionTextField(addEditIncomeViewModel = addEditIncomeViewModel)


                V_M_Space()

                Text(
                    text = stringResource(id = R.string.income_frequency),
                    style = MaterialTheme.typography.bodyLarge
                )

                Text(
                    text = stringResource(id = R.string.note_income_frequency),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            FrequencyTypePickerField(
                addEditIncomeViewModel = addEditIncomeViewModel,
                horizontalPadding = horizontalPadding.value.dp
            )
        }
    }
}

@Composable
private fun SetUpEventListener(
    incomeViewModel: IncomeViewModel,
    addEditIncomeViewModel: AddEditIncomeViewModel,
    dismiss: () -> Unit
) {
    SetUpCrudResponseEventListener(incomeViewModel = incomeViewModel, dismiss = dismiss)
    SetUpUpdateEventListener(
        incomeViewModel = incomeViewModel,
        addEditIncomeViewModel = addEditIncomeViewModel
    )
}

@Composable
private fun SetUpCrudActionDefaultReason(addEditIncomeViewModel: AddEditIncomeViewModel) {
    if (!addEditIncomeViewModel.isUpdatingConditionHandler.getValue()) {
        val defaultReason = stringResource(id = R.string.initial)
        addEditIncomeViewModel.crudActionReasonHandler.onValueChange(defaultReason)
    }
    else if (addEditIncomeViewModel.crudActionReasonHandler.getValue().isBlank()) {
        val updateReasons = stringArrayResource(id = R.array.income_update_reasons).toList()
        val defaultUpdateReason = updateReasons[0]
        addEditIncomeViewModel.crudActionReasonHandler.onValueChange(defaultUpdateReason)
    }
}

@Composable
private fun SetUpCrudResponseEventListener(incomeViewModel: IncomeViewModel, dismiss: () -> Unit) {
    LaunchedEffect(key1 = true) {
        incomeViewModel.crudResponseEventListener.collect {
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
    incomeViewModel: IncomeViewModel,
    addEditIncomeViewModel: AddEditIncomeViewModel
) {
    val showUpdateReasonDialog = rememberSaveable { mutableStateOf(false) }

    LaunchedEffect(key1 = true) {
        incomeViewModel.updateIncomeEventListener.collect {
            showUpdateReasonDialog.value = true
        }
    }

    val updateIncome: () -> Unit = {
        incomeViewModel.updateIncome(
            reason = addEditIncomeViewModel.crudActionReasonHandler.getValue(),
            addEditIncomeViewModel.income
        )
    }

    if (showUpdateReasonDialog.value) {
        ReasonPickerDialog(
            title = stringResource(id = R.string.update_income),
            reasonSupportingText = stringResource(id = R.string.income_update_supporting_text),
            reasons = stringArrayResource(id = R.array.income_update_reasons).toList(),
            preselectedReason = addEditIncomeViewModel.crudActionReasonHandler.getValue(),
            positiveActionText = stringResource(id = R.string.update),
            onPositiveActionClick = updateIncome,
            onDoNotSpecifyClick = {
                addEditIncomeViewModel.crudActionReasonHandler.onValueChange("")
                updateIncome()
            },
            onReasonChange = addEditIncomeViewModel.crudActionReasonHandler.onValueChange,
            onDismiss = { showUpdateReasonDialog.value = false }
        )
    }
}

@Composable
private fun TitleTextField(addEditIncomeViewModel: AddEditIncomeViewModel) {
    val title by addEditIncomeViewModel.titleStateFlowHandler.valueStateFlow.collectAsState()

    TitleInput(
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Next,
        title = title,
        onValueChange = addEditIncomeViewModel.titleStateFlowHandler.onValueChange
    )
}

@Composable
private fun AmountTextField(addEditIncomeViewModel: AddEditIncomeViewModel) {
    val currencySymbol by addEditIncomeViewModel.currencySymbol.collectAsState()

    AmountInput(
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Next,
        income = addEditIncomeViewModel.amountStateFlowHandler.getValue(),
        onValueChange = addEditIncomeViewModel.amountStateFlowHandler.onValueChange,
        currencySymbol = currencySymbol
    )
}

@Composable
private fun DescriptionTextField(addEditIncomeViewModel: AddEditIncomeViewModel) {
    val description by addEditIncomeViewModel.descriptionStateFlowHandler.valueStateFlow.collectAsState()

    DescriptionInput(
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Done,
        description = description,
        onValueChange = addEditIncomeViewModel.descriptionStateFlowHandler.onValueChange
    )
}

@Composable
private fun FrequencyTypePickerField(
    addEditIncomeViewModel: AddEditIncomeViewModel,
    horizontalPadding: Dp
) {
    val selectedFrequency by addEditIncomeViewModel.frequencyTypeStateFlowHandler.valueStateFlow.collectAsState()
    val selectedFrequencyNote = stringResource(id = when(selectedFrequency) {
        FrequencyType.ONCE_OFF -> R.string.note_select_income_date
        FrequencyType.DAILY -> R.string.empty
        FrequencyType.WEEKLY -> R.string.note_select_income_week_day
        FrequencyType.MONTHLY -> R.string.note_select_income_month_day
        FrequencyType.YEARLY -> R.string.note_select_yearly_income_date
    })

    FrequencyTypePicker(
        selectedFrequency = selectedFrequency,
        horizontalPadding = horizontalPadding.value.dp,
        onFrequencySelected = { type ->
            addEditIncomeViewModel.frequencyWhenStateFlowHandler.onValueChange(
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
            addEditIncomeViewModel.frequencyTypeStateFlowHandler.onValueChange(type)
        }
    )

    FrequencyWhenPicker(
        selectedFrequency = selectedFrequency,
        selectedValue = addEditIncomeViewModel.frequencyWhenStateFlowHandler.getValue(),
        frequencyNote = selectedFrequencyNote,
        horizontalPadding = horizontalPadding.value.dp,
        onValueChange = addEditIncomeViewModel.frequencyWhenStateFlowHandler.onValueChange
    )
}

@Composable
private fun getOrientationActions(
    incomeViewModel: IncomeViewModel,
    addEditIncomeViewModel: AddEditIncomeViewModel,
    isInLandscape: Boolean,
    onDismiss: () -> Unit
): @Composable RowScope.() -> Unit {
    val isUpdating = addEditIncomeViewModel.isUpdatingConditionHandler.getValue()
    val saveUpdateText: String =
        stringResource(id = if (isUpdating) R.string.update_income else R.string.save_income)

    val saveOrUpdateIncomeClick: () -> Unit = {
        if (isUpdating) {
            incomeViewModel.emitUpdateIncomeEvent()
        } else {
            incomeViewModel.saveIncome(
                reason = addEditIncomeViewModel.crudActionReasonHandler.getValue(),
                addEditIncomeViewModel.income
            )
        }
    }

    val allInputsEntered by addEditIncomeViewModel.requiredInputsStateFlowHandler.valueStateFlow.collectAsState()

    return buildOrientationAwareActions(
        isInLandscape = isInLandscape,
        saveUpdateText = saveUpdateText,
        allInputsEntered = allInputsEntered,
        onSaveOrUpdateClickListener = saveOrUpdateIncomeClick,
        onDismiss = onDismiss
    )
}
