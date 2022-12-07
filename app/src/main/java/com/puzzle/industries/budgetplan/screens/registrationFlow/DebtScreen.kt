package com.puzzle.industries.budgetplan.screens.registrationFlow

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.hilt.navigation.compose.hiltViewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.buttons.CheckboxButton
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.DebtViewModel

@Composable
fun DebtScreen(
    modifier: Modifier = Modifier,
    viewModel: DebtViewModel = hiltViewModel(),
    onContinueClick: (Boolean) -> Unit
) {
    val debtEnabled by viewModel.sub.collectAsState()

    Box(modifier = modifier.fillMaxSize(), contentAlignment = Alignment.Center) {

        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.large)) {

            GuideFlowComponents.InfoSection(
                title = stringResource(id = R.string.debt),
                message = stringResource(id = R.string.guide_allow_debt),
                note = stringResource(id = R.string.note_changeable_in_settings)
            )

            DebtCheckbox(
                checked = debtEnabled,
                onCheckedChange = { viewModel.publishValue(value = it) })

            V_M_Space()

            GuideFlowComponents.Continue(onContinueClick = { onContinueClick(debtEnabled) })
        }
    }
}


@Composable
private fun DebtCheckbox(checked: Boolean = false, onCheckedChange: (Boolean) -> Unit) {
    Row {
        CheckboxButton(checked = checked, onCheckChanged = onCheckedChange) {
            Text(text = stringResource(id = R.string.allow_debt))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDebtScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        DebtScreen(modifier = Modifier.fillMaxSize()){}
    }
}