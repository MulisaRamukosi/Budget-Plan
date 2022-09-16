@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.screens.intro.registrationGuideFlow

import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun IncomeInputScreen() {
    Box(modifier = Modifier.fillMaxSize(), contentAlignment = Alignment.Center) {
        IncomeInputSection(
            modifier = Modifier.padding(all = MaterialTheme.spacing.large),
            onContinueClick = {}
        )
    }
}

@Composable
private fun IncomeInputSection(modifier: Modifier, onContinueClick: () -> Unit) {
    Column(modifier = modifier) {
        GuideFlowComponents.InfoSection(
            title = stringResource(id = R.string.income),
            message = stringResource(id = R.string.guide_input_income),
            note = stringResource(id = R.string.note_income_addition)
        )
        IncomeInput()
        V_M_Space()
        GuideFlowComponents.Continue(onContinueClick = onContinueClick)
    }
}

@Composable
private fun IncomeInput(modifier: Modifier = Modifier) {
    OutlinedTextField(
        modifier = modifier,
        value = "",
        onValueChange = {},
        singleLine = true,
        label = {
            Text(
                text = stringResource(id = R.string.amount)
            )
        })
}


@Preview(showBackground = true)
@Composable
fun PreviewIncomeInputScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        IncomeInputScreen()
    }
}