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
import com.puzzle.industries.budgetplan.components.spacer.V_XS_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.configs.TextFieldsConfig

@Composable
fun TitleInput(
    modifier: Modifier = Modifier,
    title: String,
    maxCharCount: Int = TextFieldsConfig.SINGLE_LINE_TEXT_MAX_CHAR,
    imeAction: ImeAction = ImeAction.Default,
    onValueChange: (String) -> Unit
) {
    val focusManager = LocalFocusManager.current

    Column {
        OutlinedTextField(
            modifier = modifier,
            value = title,
            onValueChange = {if (it.length <= maxCharCount) onValueChange(it) },
            singleLine = true,
            label = { Text(text = stringResource(id = R.string.title)) },
            keyboardOptions = KeyboardOptions(
                imeAction = imeAction,
                keyboardType = KeyboardType.Text
            ),
            keyboardActions = KeyboardActions(onDone = { focusManager.clearFocus() }),
            supportingText = {
                Text(
                    text = stringResource(id = R.string.input_required),
                    style = MaterialTheme.typography.labelSmall
                )
            }
        )
    }
}

@Preview(showBackground = true)
@Composable
private fun PreviewAmountInput() {
    BudgetPlanTheme {
        TitleInput(title = "", onValueChange = {})
    }
}