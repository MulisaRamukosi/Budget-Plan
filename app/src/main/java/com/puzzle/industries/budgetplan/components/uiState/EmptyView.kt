package com.puzzle.industries.budgetplan.components.uiState

import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun EmptyView(modifier: Modifier = Modifier, message: String) {

    Box(modifier = modifier, contentAlignment = Alignment.Center) {
        Column(
            modifier = Modifier.padding(all = MaterialTheme.spacing.medium),
            horizontalAlignment = Alignment.CenterHorizontally
        ) {
            Image(
                modifier = Modifier.sizeIn(maxWidth = 200.dp, maxHeight = 200.dp),
                painter = painterResource(id = R.drawable.ic_empty),
                contentDescription = stringResource(
                    id = R.string.desc_empty_icon
                )
            )

            V_M_Space()

            Text(text = message, style = MaterialTheme.typography.bodyLarge, textAlign = TextAlign.Center)
        }
    }
}

@Composable
@Preview(showBackground = true, showSystemUi = true)
private fun EmptyViewPreview() {
    EmptyView(message = "some test message")
}