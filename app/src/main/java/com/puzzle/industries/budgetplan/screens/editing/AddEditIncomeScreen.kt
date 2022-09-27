@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.screens.editing

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.appBar.topAppBar.topAppBar
import com.puzzle.industries.budgetplan.components.inputs.AmountInput
import com.puzzle.industries.budgetplan.components.inputs.DescriptionInput
import com.puzzle.industries.budgetplan.components.inputs.TitleInput
import com.puzzle.industries.budgetplan.components.picker.DayAndMonthPicker
import com.puzzle.industries.budgetplan.components.picker.FrequencyDatePicker
import com.puzzle.industries.budgetplan.components.picker.FrequencyTypePicker
import com.puzzle.industries.budgetplan.components.picker.WeekDayPicker
import com.puzzle.industries.budgetplan.components.spacer.V_L_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.ext.observeAsNullSafeState
import com.puzzle.industries.budgetplan.factory.FrequencyDateFactory
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.budget.AddEditIncomeViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.IncomeViewModel
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.FrequencyDate
import com.puzzle.industries.domain.models.income.Income

@Composable
fun AddEditIncomeScreen(
    incomeViewModel: IncomeViewModel,
    addEditIncomeViewModel: AddEditIncomeViewModel,
    onNavigateBackToParent: () -> Unit
) {

    val title by addEditIncomeViewModel.title.observeAsNullSafeState(initial = "")
    val amount by addEditIncomeViewModel.amount.observeAsNullSafeState(initial = 0.0)
    val description by addEditIncomeViewModel.description.observeAsNullSafeState(initial = "")
    val selectedFrequency by addEditIncomeViewModel.frequencyType.observeAsNullSafeState(initial = FrequencyType.MONTHLY)
    val saveUpdateResponse = incomeViewModel.response.observeAsState()

    saveUpdateResponse.value?.let {
        incomeViewModel.reactToResponseOnce {
            if (it.response) {
                onNavigateBackToParent()
            } else {
                //TODO: navigate to parent
            }
        }
    }

    Scaffold(
        topBar = topAppBar(
            title = stringResource(id = R.string.income),
            isHomeEnabled = true,
            onHomeClick = onNavigateBackToParent
        )
    ) {
        Column(
            modifier = Modifier
                .padding(paddingValues = it)
                .verticalScroll(state = rememberScrollState())
        ) {

            Column(modifier = Modifier.padding(all = MaterialTheme.spacing.medium)) {
                TitleInput(
                    modifier = Modifier.fillMaxWidth(),
                    imeAction = ImeAction.Next,
                    title = title,
                    onValueChange = addEditIncomeViewModel.onTitleChange
                )

                V_M_Space()

                AmountInput(
                    modifier = Modifier.fillMaxWidth(),
                    imeAction = ImeAction.Next,
                    income = amount,
                    onValueChange = addEditIncomeViewModel.onAmountChange
                )

                V_M_Space()

                DescriptionInput(
                    modifier = Modifier.fillMaxWidth(),
                    imeAction = ImeAction.Done,
                    description = description,
                    onValueChange = addEditIncomeViewModel.onDescriptionChange
                )

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

            FrequencyTypePicker(
                selectedFrequency = selectedFrequency,
                horizontalPadding = MaterialTheme.spacing.medium,
                onFrequencySelected = addEditIncomeViewModel.onFrequencyTypeChange
            )

            when (selectedFrequency) {
                FrequencyType.MONTHLY -> MonthlyFrequencyPicker(addEditIncomeViewModel = addEditIncomeViewModel)
                FrequencyType.ONCE_OFF -> OnceOffFrequencyPicker(addEditIncomeViewModel = addEditIncomeViewModel)
                FrequencyType.WEEKLY -> WeeklyFrequencyPicker(addEditIncomeViewModel = addEditIncomeViewModel)
                FrequencyType.YEARLY -> YearlyFrequencyPicker(addEditIncomeViewModel = addEditIncomeViewModel)
                FrequencyType.DAILY -> addEditIncomeViewModel.onFrequencyWhenChange("")
            }

            V_L_Space()

            Button(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                enabled = addEditIncomeViewModel.allRequiredInputsProvided(),
                onClick = {
                    onSaveUpdateIncome(
                        incomeViewModel = incomeViewModel,
                        income = addEditIncomeViewModel.income,
                        isUpdating = addEditIncomeViewModel.isUpdating
                    )
                }
            ) {
                Text(text = stringResource(id = if (addEditIncomeViewModel.isUpdating) R.string.update_income else R.string.save_income))
            }

            V_M_Space()
        }
    }
}

private fun onSaveUpdateIncome(
    incomeViewModel: IncomeViewModel,
    income: Income,
    isUpdating: Boolean
) {
    if (isUpdating) {
        //TODO: show popup to ask use to provide reason for update
        incomeViewModel.updateIncome(income = income, reason = "")
    } else {
        //TODO: show popup to ask use to provide reason for insert
        incomeViewModel.saveIncome(income = income, reason = income.description)
    }

}

@Composable
private fun WeeklyFrequencyPicker(addEditIncomeViewModel: AddEditIncomeViewModel) {
    V_M_Space()

    val selectedDay by addEditIncomeViewModel.frequencyWhen.observeAsNullSafeState(initial = WeekDays.MONDAY.name)

    Column {
        Text(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.note_select_income_week_day),
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        WeekDayPicker(
            horizontalPadding = MaterialTheme.spacing.medium,
            onDaySelected = { weekDay -> addEditIncomeViewModel.onFrequencyWhenChange(weekDay.name) },
            selectedDay = WeekDays.valueOf(selectedDay)
        )
    }

}

@Composable
private fun MonthlyFrequencyPicker(addEditIncomeViewModel: AddEditIncomeViewModel) {
    V_M_Space()

    val selectedDay by addEditIncomeViewModel.frequencyWhen.observeAsNullSafeState(initial = "1")

    Column(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium)) {
        Text(
            text = stringResource(id = R.string.note_select_income_month_day),
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        com.puzzle.industries.budgetplan.components.picker.DayOfMonthPicker(
            modifier = Modifier.fillMaxWidth(),
            day = selectedDay.toInt(),
            onClick = { day -> addEditIncomeViewModel.onFrequencyWhenChange(day.toString()) }
        )
    }

}

@Composable
private fun OnceOffFrequencyPicker(addEditIncomeViewModel: AddEditIncomeViewModel) {
    V_M_Space()
    val currDate = FrequencyDateFactory.createCurrentDate()
    val selectedDate by addEditIncomeViewModel.frequencyWhen.observeAsNullSafeState(initial = currDate.toString())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium)
    ) {
        Text(
            text = stringResource(id = R.string.note_select_income_date),
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        FrequencyDatePicker(
            preselectedFrequencyDate = FrequencyDate.fromString(date = selectedDate),
            onDateSelected = { date -> addEditIncomeViewModel.onFrequencyWhenChange(date.toString()) }
        )
    }
}

@Composable
fun YearlyFrequencyPicker(addEditIncomeViewModel: AddEditIncomeViewModel) {
    V_M_Space()
    val currDate = FrequencyDateFactory.createCurrentDate()
    val selectedDate by addEditIncomeViewModel.frequencyWhen.observeAsNullSafeState(initial = currDate.toString())

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = MaterialTheme.spacing.medium)
    ) {
        Text(
            text = stringResource(id = R.string.note_select_yearly_income_date),
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        DayAndMonthPicker(
            modifier = Modifier.fillMaxWidth(),
            preselectedFrequencyDate = FrequencyDate.fromString(date = selectedDate),
            onDateSelected = { day, month ->
                val date = FrequencyDate(day = day, month = month, year = 0)
                addEditIncomeViewModel.onFrequencyWhenChange(date.toDayMonthString())
            }
        )
    }
}

@Preview
@Composable
fun PreviewAddEditIncomeScreen() {
    BudgetPlanTheme {
        AddEditIncomeScreen(
            incomeViewModel = hiltViewModel(),
            addEditIncomeViewModel = AddEditIncomeViewModel(prevIncome = null),
            onNavigateBackToParent = {}
        )
    }
}

