@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalMaterial3WindowSizeClassApi::class
)

package com.puzzle.industries.budgetplan.screens.intro.registrationGuideFlow

import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.CheckboxButton
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun DebtScreen(modifier: Modifier = Modifier){
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        DebtSection(
            modifier = Modifier.padding(all = MaterialTheme.spacing.large),
            onContinueClick = {}
        )
    }
}

@Composable
private fun DebtSection(modifier: Modifier, onContinueClick: () -> Unit) {
    Column(modifier = modifier) {
        GuideFlowComponents.InfoSection(
            title = stringResource(id = R.string.debt),
            message = stringResource(id = R.string.guide_allow_debt),
            note = stringResource(id = R.string.note_changeable_in_settings)
        )
        DebtCheckbox(onCheckedChange = {})
        V_M_Space()
        GuideFlowComponents.Continue(onContinueClick = onContinueClick)
    }
}

@Composable
private fun DebtCheckbox(checked: Boolean = false, onCheckedChange: (Boolean) -> Unit){
    Row {
        CheckboxButton(checked = checked, onCheckChanged = onCheckedChange){
            Text(text = stringResource(id = R.string.allow_debt))
        }
    }
}

@Preview(showBackground = true)
@Composable
fun PreviewDebtScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        DebtScreen(modifier = Modifier.fillMaxSize())
    }
}