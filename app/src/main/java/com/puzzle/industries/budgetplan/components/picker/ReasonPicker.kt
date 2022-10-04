@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.picker

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.QuestionMark
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.util.configs.TextFieldsConfig

@Composable
fun ReasonPicker(
    modifier: Modifier = Modifier,
    supportingText: String,
    reason: String,
    reasons: List<String>,
    onReasonChange: (String) -> Unit
) {
    val scrollState = rememberScrollState()


    Column(modifier = modifier.verticalScroll(state = scrollState)) {

        Text(text = supportingText)

        V_M_Space()

        ValuePickerField(
            preselectedReason = reason,
            reasons = reasons,
            onReasonChange = onReasonChange
        )

        V_M_Space()
    }
}

@Composable
private fun ValuePickerField(
    preselectedReason: String,
    reasons: List<String>,
    onReasonChange: (String) -> Unit
) {
    val selectedReason = rememberSaveable { mutableStateOf(preselectedReason) }
    val otherIsSelected = rememberSaveable { mutableStateOf(false) }

    ValueDropDownPicker(
        modifier = Modifier,
        label = stringResource(id = R.string.reason),
        leadingIcon = Icons.Rounded.QuestionMark,
        leadingIconDescription = stringResource(id = R.string.question_mark_icon),
        currentValue = preselectedReason,
        values = reasons,
        onValueChange = { newReason ->
            selectedReason.value = newReason
            onReasonChange(newReason)
        }
    )

    if (selectedReason.value == stringResource(id = R.string.reason_other)) {
        otherIsSelected.value = true

        V_M_Space()
        OtherReasonTextField(onReasonChange = onReasonChange)

    } else {
        otherIsSelected.value = false
    }
}

@Composable
fun OtherReasonTextField(onReasonChange: (String) -> Unit){
    val otherReason = rememberSaveable { mutableStateOf("") }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        value = otherReason.value,
        onValueChange = {
            if (it.length <= TextFieldsConfig.singleLineTextMaxChar) {
                otherReason.value = it
                onReasonChange(otherReason.value)
            }
        },
        label = { Text(text = stringResource(id = R.string.specify_optional)) },
        maxLines = 2
    )
}

@Composable
fun reasonPickerDefaultActions(
    doNotSpecifyClick: () -> Unit,
    positiveActionText: String,
    onPositiveActionClick: () -> Unit
): @Composable RowScope.() -> Unit {
    return {
        TextButton(
            onClick = doNotSpecifyClick,
            content = { Text(text = stringResource(id = R.string.do_not_specify)) }
        )
        TextButton(onClick = onPositiveActionClick, content = { Text(text = positiveActionText) })
    }
}