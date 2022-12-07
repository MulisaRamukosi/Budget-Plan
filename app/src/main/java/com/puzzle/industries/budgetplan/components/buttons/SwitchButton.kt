package com.puzzle.industries.budgetplan.components.buttons

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Switch
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme

@Composable
fun SwitchButton(
    modifier: Modifier = Modifier,
    checked: Boolean,
    onCheckChanged: (Boolean) -> Unit,
    content: @Composable () -> Unit
) {
    Row(modifier = modifier, verticalAlignment = Alignment.CenterVertically) {
        Box(modifier = Modifier.weight(weight = 1f, fill = true)){
            content()
        }

        H_S_Space()

        Switch(
            modifier = Modifier,
            checked = checked,
            onCheckedChange = onCheckChanged)
    }

}

@Preview(showBackground = true)
@Composable
fun PreviewSwitchButton(){
    BudgetPlanTheme(dynamicColor = false) {
        SwitchButton(
            modifier = Modifier.fillMaxWidth(),
            checked = true,
            onCheckChanged = {}
        ) {
            Column {
                Text(
                    text = stringResource(id = R.string.payment_reminder),
                    style = MaterialTheme.typography.bodyMedium
                )

                Text(
                    text = stringResource(id = R.string.note_payment_reminder),
                    style = MaterialTheme.typography.bodySmall
                )
            }
        }
    }
}