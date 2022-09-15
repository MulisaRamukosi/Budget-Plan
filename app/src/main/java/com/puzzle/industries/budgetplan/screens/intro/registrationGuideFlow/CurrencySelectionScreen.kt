@file:OptIn(ExperimentalMaterial3WindowSizeClassApi::class, ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.screens.intro.registrationGuideFlow

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ArrowDropDown
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
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
fun CurrencySelectionScreen(modifier: Modifier = Modifier) {
    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        CurrencySelectionSection(
            modifier = Modifier.padding(all = MaterialTheme.spacing.large),
            onContinueClick = {}
        )
    }
}

@Composable
private fun CurrencySelectionSection(modifier: Modifier, onContinueClick: () -> Unit) {
    Column(modifier = modifier) {
        GuideFlowComponents.InfoSection(
            title = stringResource(id = R.string.currency),
            message = stringResource(id = R.string.guide_select_currency),
            note = stringResource(id = R.string.note_changeable_in_settings)
        )
        CurrencySelection()
        V_M_Space()
        GuideFlowComponents.Continue(onContinueClick = onContinueClick)
    }
}

@Composable
private fun CurrencySelection() {
    OutlinedTextField(
        value = "ZAR-South Africa",
        onValueChange = {},
        readOnly = true,
        trailingIcon = {
            Icon(
                imageVector = Icons.Rounded.ArrowDropDown,
                contentDescription = stringResource(id = R.string.desc_arrow_drop_down_icon)
            )
        })
}


@Preview(showBackground = true)
@Composable
fun PreviewCurrencySelectionScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        CurrencySelectionScreen(modifier = Modifier.fillMaxSize())
    }
}