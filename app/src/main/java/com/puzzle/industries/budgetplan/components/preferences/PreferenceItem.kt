package com.puzzle.industries.budgetplan.components.preferences

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.components.text.TitleAndDescription
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun PreferenceItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    content: @Composable () -> Unit
){
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium)) {
            TitleAndDescription(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                title = title,
                description = description
            )

            V_S_Space()

            content()
        }
    }
}