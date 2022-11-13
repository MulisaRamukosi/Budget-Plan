package com.puzzle.industries.budgetplan.components.text

import androidx.compose.foundation.layout.Column
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.text.style.TextOverflow
import com.puzzle.industries.budgetplan.components.spacer.V_XS_Space

@Composable
fun TitleAndDescription(modifier:Modifier = Modifier ,title: String, description: String = "") {
    Column(modifier = modifier) {
        Text(
            text = title,
            maxLines = 1,
            overflow = TextOverflow.Ellipsis,
            style = MaterialTheme.typography.titleMedium
        )

        if (description.isNotBlank()) {
            V_XS_Space()

            Text(
                text = description,
                style = MaterialTheme.typography.labelSmall,
                maxLines = 4,
                overflow = TextOverflow.Ellipsis
            )
        }
    }
}