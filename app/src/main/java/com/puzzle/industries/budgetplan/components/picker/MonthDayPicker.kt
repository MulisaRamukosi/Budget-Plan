@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.picker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R

@Composable
fun MonthDayPicker(day: Int, onClick: (Int) -> Unit) {
    var expanded by remember { mutableStateOf(value = false) }
    var selectedDay by remember { mutableStateOf(value = day.toString()) }

    ExposedDropdownMenuBox(expanded = expanded, onExpandedChange = {expanded = !expanded}) {
        OutlinedTextField(
            value = selectedDay,
            onValueChange = {},
            readOnly = true,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Event,
                    contentDescription = stringResource(id = R.string.desc_budget_plan_day_icon)
                )
            },
            trailingIcon = { ExposedDropdownMenuDefaults.TrailingIcon(expanded = expanded) }
        )

        ExposedDropdownMenu(
            expanded = expanded,
            onDismissRequest = { expanded = false },
        ) {
            (1..31).forEach { day ->
                DropdownMenuItem(
                    text = { Text(text = day.toString()) },
                    onClick = {
                        selectedDay = day.toString()
                        onClick(day)
                        expanded = false
                    }
                )
            }
        }
    }
}