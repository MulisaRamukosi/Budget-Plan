@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.picker

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.*
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.layout.onSizeChanged
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.unit.dp

@Composable
fun <T> ValueDropDownPicker(
    modifier: Modifier,
    label: String,
    leadingIcon: ImageVector,
    leadingIconDescription: String,
    currentValue: T,
    values: List<T>,
    onValueChange: (T) -> Unit
) {
    val dropDownWidth = remember{ mutableStateOf(0) }
    val expanded = rememberSaveable { mutableStateOf(value = false) }
    val source = remember { MutableInteractionSource() }
    val pressedState = source.interactions.collectAsState(
        initial = PressInteraction.Cancel(PressInteraction.Press(Offset.Zero))
    )

    if (pressedState.value is PressInteraction.Release) {
        expanded.value = !expanded.value
        source.tryEmit(PressInteraction.Cancel(PressInteraction.Press(Offset.Zero)))
    }

    Column(modifier = modifier.onSizeChanged { dropDownWidth.value = it.width }) {
        OutlinedTextField(
            modifier = Modifier.fillMaxWidth(),
            readOnly = true,
            value = currentValue.toString(),
            onValueChange = {},
            label = { Text(text = label, maxLines = 1, overflow = TextOverflow.Ellipsis) },
            leadingIcon = {
                Icon(imageVector = leadingIcon, contentDescription = leadingIconDescription)
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded.value) },
            interactionSource = source
        )

        DropdownMenu(
            modifier = Modifier
                .width(width = dropDownWidth.value.dp)
                .wrapContentHeight()
                .heightIn(max = 200.dp),
            expanded = expanded.value,
            onDismissRequest = { expanded.value = false }
        ) {
            values.forEach { value ->
                DropdownMenuItem(
                    text = { Text(text = value.toString()) },
                    onClick = {
                        onValueChange(value)
                        expanded.value = false
                    }
                )
            }
        }
    }
}