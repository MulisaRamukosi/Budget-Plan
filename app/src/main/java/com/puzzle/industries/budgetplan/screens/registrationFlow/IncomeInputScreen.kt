package com.puzzle.industries.budgetplan.screens.registrationFlow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.inputs.AmountInput
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.configs.FrequencyConfig
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.IncomeInputViewModel
import com.puzzle.industries.domain.models.income.Income

@Composable
fun IncomeInputScreen(
    viewModel: IncomeInputViewModel = hiltViewModel(),
    onContinueClick: (Income) -> Unit
) {

    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.large)) {

            GuideFlowComponents.InfoSection(
                title = stringResource(id = R.string.income),
                message = stringResource(id = R.string.guide_input_income),
                note = stringResource(id = R.string.note_income_addition)
            )

            AmountInput(
                income = 0.0,
                onValueChange = { viewModel.publishValue(value = it) },
                currencySymbol = ""
            )

            V_M_Space()

            ContinueButton(viewModel = viewModel, onContinueClick = onContinueClick)
        }
    }
}

@Composable
private fun ContinueButton(viewModel: IncomeInputViewModel, onContinueClick: (Income) -> Unit) {
    val income by viewModel.sub.collectAsState()
    val incomeTitle = stringResource(id = R.string.base_income)
    val incomeDescription = stringResource(id = R.string.desc_base_income)

    GuideFlowComponents.Continue(
        enabled = income > 0,
        onContinueClick = {
            val incomeModel = Income(
                frequencyType = FrequencyConfig.DEFAULT_TYPE,
                frequencyWhen = FrequencyConfig.DEFAULT_WHEN,
                amount = income,
                title = incomeTitle,
                description = incomeDescription
            )
            onContinueClick(incomeModel)
        }
    )
}

@Preview(showBackground = true)
@Composable
private fun PreviewIncomeInputScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        IncomeInputScreen {}
    }
}