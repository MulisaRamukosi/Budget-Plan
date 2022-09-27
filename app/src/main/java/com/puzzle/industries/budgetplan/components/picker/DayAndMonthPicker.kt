package com.puzzle.industries.budgetplan.components.picker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.wrapContentWidth
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import com.puzzle.industries.budgetplan.components.spacer.H_M_Space
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.domain.models.FrequencyDate

@Composable
fun DayAndMonthPicker(
    modifier: Modifier,
    preselectedFrequencyDate: FrequencyDate,
    onDateSelected: (Int, Int) -> Unit
) {
    var selectedDay by remember { mutableStateOf(preselectedFrequencyDate.day) }
    var selectedMonth by remember { mutableStateOf(preselectedFrequencyDate.month) }

    Row(modifier = modifier) {
        DayOfMonthPicker(
            modifier = Modifier.weight(weight = 0.4f),
            day = selectedDay,
            onClick = {
                selectedDay = it
                onDateSelected(selectedDay, selectedMonth)
            }
        )
        H_S_Space()
        MonthPicker(
            modifier = Modifier.weight(weight = 0.6f),
            month = selectedMonth,
            onClick = {
                selectedMonth = it
                onDateSelected(selectedDay, selectedMonth)
            }
        )
    }

}