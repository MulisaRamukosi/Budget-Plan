package com.puzzle.industries.budgetplan.components.preferences

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.components.SwitchButton
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun PreferenceSwitchItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    checked: Boolean,
    onEnabledChanged: (enabled: Boolean) -> Unit
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.medium)) {
            SwitchButton(checked = checked, onCheckChanged = onEnabledChanged){
                Text(
                    text = title,
                    maxLines = 1,
                    overflow = TextOverflow.Ellipsis,
                    style = MaterialTheme.typography.titleMedium
                )
            }

            V_S_Space()

            Text(text = description, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
@Preview
private fun PreviewPreferenceSwitchItem(){
    BudgetPlanTheme(dynamicColor = false) {
        PreferenceSwitchItem(
            title = "test title",
            description = "some description about the preference switch",
            checked = true,
            onEnabledChanged = {}
        )
    }

}