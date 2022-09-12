package com.puzzle.industries.budgetplan.components

import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R

@Composable
fun HomeCardTitle(modifier: Modifier, text: String) {
    Text(
        modifier = modifier,
        text = text,
        style = MaterialTheme.typography.bodyLarge
    )
}