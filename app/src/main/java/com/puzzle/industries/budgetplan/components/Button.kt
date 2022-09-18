package com.puzzle.industries.budgetplan.components

import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.calculateStartPadding
import androidx.compose.material3.*
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.unit.LayoutDirection
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun TrailingIconButton(
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    imageVector: ImageVector,
    text: String,
    contentDescription: String,
    onClick: () -> Unit
){
    val buttonDefaultContentPadding = ButtonDefaults.ContentPadding
    val buttonContentPadding = PaddingValues(
        start = buttonDefaultContentPadding.calculateStartPadding(layoutDirection = LayoutDirection.Ltr),
        top = buttonDefaultContentPadding.calculateTopPadding(),
        bottom = buttonDefaultContentPadding.calculateBottomPadding(),
        end = MaterialTheme.spacing.medium
    )
    Button(
        modifier = modifier,
        contentPadding = buttonContentPadding,
        enabled = enabled,
        onClick = onClick
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(text = text)

            Icon(
                imageVector = imageVector,
                contentDescription = contentDescription
            )
        }
    }
}