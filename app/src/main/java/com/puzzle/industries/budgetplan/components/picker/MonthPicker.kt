@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.picker

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.width
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.layout.onGloballyPositioned
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.domain.constants.Months
import java.util.Calendar

@Composable
fun MonthPicker(modifier: Modifier = Modifier, month: Int, onClick: (Int) -> Unit) {

    val density = LocalDensity.current
    var expanded by remember { mutableStateOf(value = false) }
    var selectedMonth by remember { mutableStateOf(value = month) }
    var width by remember { mutableStateOf(0.dp) }
    val source = remember { MutableInteractionSource() }
    val pressedState = source.interactions.collectAsState(
        initial = PressInteraction.Cancel(PressInteraction.Press(Offset.Zero))
    )

    if (pressedState.value is PressInteraction.Release) {
        expanded = !expanded
        source.tryEmit(PressInteraction.Cancel(PressInteraction.Press(Offset.Zero)))
    }

    Column(modifier = modifier) {
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .onGloballyPositioned { coordinates ->
                    width = density.run { coordinates.size.width.toDp() }
                },
            readOnly = true,
            value = Months.values()[selectedMonth].name,
            onValueChange = {},
            label = { Text(text = stringResource(id = R.string.month)) },
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Event,
                    contentDescription = stringResource(id = R.string.desc_date_icon)
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) },
            interactionSource = source
        )

        DropdownMenu(
            modifier = Modifier
                .width(width = width)
                .height(height = 200.dp),
            expanded = expanded,
            onDismissRequest = { expanded = false }
        ) {
            val months = stringArrayResource(id = R.array.months)
            Months.values().forEach { day ->
                DropdownMenuItem(
                    text = { Text(text = months[day.ordinal]) },
                    onClick = {
                        selectedMonth = day.ordinal
                        onClick(selectedMonth)
                        expanded = false
                    }
                )
            }
        }


    }


}