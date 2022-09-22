@file:OptIn(
    ExperimentalMaterial3Api::class
)

package com.puzzle.industries.budgetplan.screens.registration

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.viewmodel.compose.viewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.IncomeInputViewModel
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.income.Income

@Composable
fun IncomeInputScreen(
    viewModel: IncomeInputViewModel = viewModel(),
    onContinueClick: (Income) -> Unit = {}
) {

    val income by viewModel.sub.observeAsState(initial = 0.0)
    val incomeTitle = stringResource(id = R.string.base_income)
    val incomeDescription = stringResource(id = R.string.desc_base_income)

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.large)) {

            GuideFlowComponents.InfoSection(
                title = stringResource(id = R.string.income),
                message = stringResource(id = R.string.guide_input_income),
                note = stringResource(id = R.string.note_income_addition)
            )

            IncomeInput(income = income, onValueChange = { viewModel.pub.value = it })

            V_M_Space()

            GuideFlowComponents.Continue(
                enabled = income > 0,
                onContinueClick = {
                    val incomeModel = Income(
                        frequency = Frequency.MONTHLY,
                        amount = income,
                        title = incomeTitle,
                        description = incomeDescription
                    )
                    onContinueClick(incomeModel)
                }
            )
        }
    }
}

@Composable
private fun IncomeInput(
    modifier: Modifier = Modifier,
    income: Double,
    onValueChange: (Double) -> Unit
) {
    val focusManager = LocalFocusManager.current

    OutlinedTextField(
        modifier = modifier,
        value = if (income == 0.0) "" else income.toString(),
        onValueChange = {
            try {
                onValueChange(it.toDouble())
            } catch (ex: Exception) {
                ex.printStackTrace()
            }
        },
        singleLine = true,
        label = { Text(text = stringResource(id = R.string.amount)) },
        keyboardOptions = KeyboardOptions(keyboardType = KeyboardType.Decimal),
        keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
    )
}


@Preview(showBackground = true)
@Composable
fun PreviewIncomeInputScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        IncomeInputScreen()
    }
}