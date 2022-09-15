package com.puzzle.industries.budgetplan.components

import androidx.compose.foundation.layout.*
import androidx.compose.material3.Checkbox
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.components.spacer.H_L_Space
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun CheckboxButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {

        Checkbox(checked = checked, onCheckedChange = onCheckChanged)

        content()

        H_L_Space()
    }
}

@Preview
@Composable
private fun PreviewCheckboxButton(){
    CheckboxButton(modifier = Modifier,  checked = false, onCheckChanged = {}) {
        Text(text = "preview text")
    }
}