@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.inputs

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.text.KeyboardActions
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalFocusManager
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.components.spacer.V_XS_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun AmountInput(
    modifier: Modifier = Modifier,
    income: Double,
    imeAction: ImeAction = ImeAction.Default,
    onValueChange: (Double) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        OutlinedTextField(
            modifier = modifier,
            value = if (income == 0.0) "" else income.toBigDecimal().toPlainString(),
            onValueChange = {
                try {
                    if(it.length < 15) onValueChange(it.toDouble())
                } catch (ex: Exception) {
                    ex.printStackTrace()
                }
            },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.amount)) },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = KeyboardType.Decimal
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() })
        )
        V_XS_Space()
        Text(
            modifier = Modifier.padding(start = MaterialTheme.spacing.medium),
            text = stringResource(id = R.string.input_required),
            style = MaterialTheme.typography.labelSmall
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAmountInput() {
    BudgetPlanTheme {
        AmountInput(income = 0.0, onValueChange = {})
    }
}