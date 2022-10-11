package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.foundation.layout.Row
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Remove
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import com.puzzle.industries.budgetplan.R

@Composable
fun OutcomeAmount(
    currencySymbol: String,
    amountTextStyle: TextStyle = MaterialTheme.typography.headlineSmall,
    amount: Double
) {
    Row(verticalAlignment = Alignment.CenterVertically) {
        Icon(
            imageVector = Icons.Rounded.Remove,
            contentDescription = stringResource(id = R.string.desc_outcome_icon),
            tint = MaterialTheme.colorScheme.error
        )

        Text(
            text = stringResource(id = R.string.currency_amount, currencySymbol, amount),
            color = MaterialTheme.colorScheme.primary,
            style = amountTextStyle
        )
    }
}