package com.puzzle.industries.budgetplan.screens.budget.income

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Button
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
import com.puzzle.industries.budgetplan.components.dialog.viewAlertDialogDefaultActions
import com.puzzle.industries.budgetplan.components.inputs.AmountInput
import com.puzzle.industries.budgetplan.components.inputs.DescriptionInput
import com.puzzle.industries.budgetplan.components.inputs.TitleInput
import com.puzzle.industries.budgetplan.components.layout.OrientationAwareContentWrapper
import com.puzzle.industries.budgetplan.components.picker.*
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.viewModels.budget.income.AddEditIncomeViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.FrequencyDate
import com.puzzle.industries.domain.models.income.Income

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
        addEditIncomeViewModel.onCrudActionReasonChange(defaultReason)
    } else if (addEditIncomeViewModel.crudActionReason.isBlank()) {
        val updateReasons = stringArrayResource(id = R.array.income_update_reasons).toList()
        val defaultUpdateReason = updateReasons[0]
        addEditIncomeViewModel.onCrudActionReasonChange(defaultUpdateReason)
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
            income = addEditIncomeViewModel.income,
            reason = addEditIncomeViewModel.crudActionReason
        )
    }

    if (showUpdateReasonDialog.value) {
        ReasonPickerDialog(
            title = stringResource(id = R.string.update_income),
            reasonSupportingText = stringResource(id = R.string.income_update_supporting_text),
            reasons = stringArrayResource(id = R.array.income_update_reasons).toList(),
            preselectedReason = addEditIncomeViewModel.crudActionReason,
            positiveActionText = stringResource(id = R.string.update),
            onPositiveActionClick = updateIncome,
            onDoNotSpecifyClick = {
                addEditIncomeViewModel.onCrudActionReasonChange("")
                updateIncome()
            },
            onReasonChange = addEditIncomeViewModel.onCrudActionReasonChange,
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
    val amount by addEditIncomeViewModel.amountStateFlowHandler.valueStateFlow.collectAsState()

    AmountInput(
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Next,
        income = amount,
        onValueChange = addEditIncomeViewModel.amountStateFlowHandler.onValueChange
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

    FrequencyTypePicker(
        selectedFrequency = selectedFrequency,
        horizontalPadding = horizontalPadding.value.dp,
        onFrequencySelected = addEditIncomeViewModel.frequencyTypeStateFlowHandler.onValueChange
    )

    FrequencyWhenPicker(
        selectedFrequency = selectedFrequency,
        addEditIncomeViewModel = addEditIncomeViewModel,
        horizontalPadding = horizontalPadding.value.dp
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
    val saveUpdateText: String = stringResource(id = if (isUpdating) R.string.update_income else R.string.save_income)

    val saveOrUpdateIncomeClick: () -> Unit = {
        onSaveUpdateIncome(
            incomeViewModel = incomeViewModel,
            income = addEditIncomeViewModel.income,
            reason = addEditIncomeViewModel.crudActionReason,
            isUpdating = isUpdating
        )
    }

    val allInputsEntered by addEditIncomeViewModel.requiredInputsStateFlowHandler.valueStateFlow.collectAsState()

    return when (isInLandscape) {
        true -> viewAlertDialogDefaultActions(
            onCancelClick = onDismiss,
            positiveActionText = saveUpdateText,
            positiveActionEnabled = allInputsEntered,
            onPositiveActionClick = saveOrUpdateIncomeClick
        )
        else -> normalViewActions(
            allInputsEntered = allInputsEntered,
            saveUpdateText = saveUpdateText,
            saveUpdateClickListener = saveOrUpdateIncomeClick
        )
    }
}

@Composable
private fun normalViewActions(
    allInputsEntered: Boolean,
    saveUpdateText: String,
    saveUpdateClickListener: () -> Unit
): @Composable RowScope.() -> Unit {
    return {

        Button(
            enabled = allInputsEntered,
            onClick = saveUpdateClickListener,
            content = { Text(text = saveUpdateText) }
        )
    }
}

@Composable
private fun FrequencyWhenPicker(
    selectedFrequency: FrequencyType,
    addEditIncomeViewModel: AddEditIncomeViewModel,
    horizontalPadding: Dp
) {
    when (selectedFrequency) {
        FrequencyType.MONTHLY -> MonthlyFrequencyPicker(
            addEditIncomeViewModel = addEditIncomeViewModel,
            horizontalPadding = horizontalPadding
        )
        FrequencyType.ONCE_OFF -> OnceOffFrequencyPicker(
            addEditIncomeViewModel = addEditIncomeViewModel,
            horizontalPadding = horizontalPadding
        )
        FrequencyType.WEEKLY -> WeeklyFrequencyPicker(
            addEditIncomeViewModel = addEditIncomeViewModel,
            horizontalPadding = horizontalPadding
        )
        FrequencyType.YEARLY -> YearlyFrequencyPicker(
            addEditIncomeViewModel = addEditIncomeViewModel,
            horizontalPadding = horizontalPadding
        )
        FrequencyType.DAILY -> addEditIncomeViewModel.frequencyWhenStateFlowHandler.onValueChange("")
    }
}

private fun onSaveUpdateIncome(
    incomeViewModel: IncomeViewModel,
    income: Income,
    reason: String,
    isUpdating: Boolean
) {
    if (isUpdating) {
        incomeViewModel.emitUpdateIncomeEvent()
    } else {
        incomeViewModel.saveIncome(income = income, reason = reason)
    }
}

@Composable
private fun WeeklyFrequencyPicker(
    addEditIncomeViewModel: AddEditIncomeViewModel,
    horizontalPadding: Dp
) {
    V_M_Space()

    val selectedDay by addEditIncomeViewModel.frequencyWhenStateFlowHandler.valueStateFlow.collectAsState()

    Column {
        Text(
            modifier = Modifier.padding(horizontal = horizontalPadding),
            text = stringResource(id = R.string.note_select_income_week_day),
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        WeekDayPicker(
            horizontalPadding = horizontalPadding,
            onDaySelected = { weekDay ->
                addEditIncomeViewModel.frequencyWhenStateFlowHandler.onValueChange(
                    weekDay.name
                )
            },
            selectedDay = WeekDays.valueOf(selectedDay)
        )
    }
}

@Composable
private fun MonthlyFrequencyPicker(
    addEditIncomeViewModel: AddEditIncomeViewModel,
    horizontalPadding: Dp
) {
    V_M_Space()

    val selectedDay by addEditIncomeViewModel.frequencyWhenStateFlowHandler.valueStateFlow.collectAsState()

    Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
        Text(
            text = stringResource(id = R.string.note_select_income_month_day),
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()

        DayOfMonthPicker(modifier = Modifier.fillMaxWidth(),
            day = selectedDay.toInt(),
            onClick = { day ->
                addEditIncomeViewModel.frequencyWhenStateFlowHandler.onValueChange(
                    day.toString()
                )
            })
    }

}

@Composable
private fun OnceOffFrequencyPicker(
    addEditIncomeViewModel: AddEditIncomeViewModel,
    horizontalPadding: Dp
) {
    V_M_Space()
    val selectedDate by addEditIncomeViewModel.frequencyWhenStateFlowHandler.valueStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    ) {
        Text(
            text = stringResource(id = R.string.note_select_income_date),
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        FrequencyDatePicker(preselectedFrequencyDate = FrequencyDate.parse(date = selectedDate),
            onDateSelected = { date ->
                addEditIncomeViewModel.frequencyWhenStateFlowHandler.onValueChange(
                    date.toString()
                )
            })
    }
}

@Composable
private fun YearlyFrequencyPicker(
    addEditIncomeViewModel: AddEditIncomeViewModel,
    horizontalPadding: Dp
) {
    V_M_Space()

    val selectedDate by addEditIncomeViewModel.frequencyWhenStateFlowHandler.valueStateFlow.collectAsState()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    ) {
        Text(
            text = stringResource(id = R.string.note_select_yearly_income_date),
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        DayAndMonthPicker(modifier = Modifier.fillMaxWidth(),
            preselectedFrequencyDate = FrequencyDate.parse(date = selectedDate),
            onDateSelected = { day, month ->
                val date = FrequencyDate(day = day, month = month, year = 0)
                addEditIncomeViewModel.frequencyWhenStateFlowHandler.onValueChange(date.toDayMonthString())
            })
    }
}
