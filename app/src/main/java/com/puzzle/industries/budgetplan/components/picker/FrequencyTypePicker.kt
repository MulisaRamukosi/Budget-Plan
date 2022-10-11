@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.picker

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.unit.Dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.FrequencyDate

@Composable
fun FrequencyTypePicker(
    modifier: Modifier = Modifier,
    selectedFrequency: FrequencyType,
    horizontalPadding: Dp,
    onFrequencySelected: (FrequencyType) -> Unit
) {
    val chipRowScrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(state = chipRowScrollState)
            .padding(horizontal = horizontalPadding)
    ) {
        val frequencyTypes = stringArrayResource(id = R.array.frequency)

        FrequencyType.values().forEach {
            FilterChip(
                selected = selectedFrequency == it,
                label = { Text(text = frequencyTypes[it.ordinal]) },
                onClick = { if (it != selectedFrequency) onFrequencySelected(it) }
            )
            H_S_Space()
        }
    }
}

@Composable
fun FrequencyWhenPicker(
    selectedFrequency: FrequencyType,
    selectedValue: String,
    frequencyNote: String,
    horizontalPadding: Dp,
    onValueChange: (String) -> Unit
) {
    when (selectedFrequency) {
        FrequencyType.ONCE_OFF -> OnceOffFrequencyPicker(
            selectedValue = selectedValue,
            frequencyNote = frequencyNote,
            horizontalPadding = horizontalPadding,
            onValueChange = onValueChange
        )

        FrequencyType.DAILY -> onValueChange("")

        FrequencyType.WEEKLY -> WeeklyFrequencyPicker(
            selectedValue = selectedValue,
            frequencyNote = frequencyNote,
            horizontalPadding = horizontalPadding,
            onValueChange = onValueChange
        )

        FrequencyType.MONTHLY -> MonthlyFrequencyPicker(
            selectedValue = selectedValue,
            frequencyNote = frequencyNote,
            horizontalPadding = horizontalPadding,
            onValueChange = onValueChange
        )

        FrequencyType.YEARLY -> YearlyFrequencyPicker(
            selectedValue = selectedValue,
            frequencyNote = frequencyNote,
            horizontalPadding = horizontalPadding,
            onValueChange = onValueChange
        )

    }
}

@Composable
private fun WeeklyFrequencyPicker(
    selectedValue: String,
    frequencyNote: String,
    horizontalPadding: Dp,
    onValueChange: (String) -> Unit
) {
    V_M_Space()

    val selectedDay = remember {
        mutableStateOf(selectedValue)
    }

    Column {
        Text(
            modifier = Modifier.padding(horizontal = horizontalPadding),
            text = frequencyNote,
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        WeekDayPicker(
            horizontalPadding = horizontalPadding,
            onDaySelected = { weekDay ->
                onValueChange(weekDay.name)
                selectedDay.value = weekDay.name
            },
            selectedDay = WeekDays.valueOf(selectedDay.value)
        )
    }
}

@Composable
private fun MonthlyFrequencyPicker(
    selectedValue: String,
    frequencyNote: String,
    horizontalPadding: Dp,
    onValueChange: (String) -> Unit
) {
    V_M_Space()

    Column(modifier = Modifier.padding(horizontal = horizontalPadding)) {
        Text(
            text = frequencyNote,
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()

        DayOfMonthPicker(
            modifier = Modifier.fillMaxWidth(),
            day = selectedValue.toInt(),
            onClick = { day -> onValueChange(day.toString()) }
        )
    }

}

@Composable
private fun OnceOffFrequencyPicker(
    selectedValue: String,
    frequencyNote: String,
    horizontalPadding: Dp,
    onValueChange: (String) -> Unit
) {
    V_M_Space()

    val selectedDate = remember {
        mutableStateOf(selectedValue)
    }
    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    ) {
        Text(
            text = frequencyNote,
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        FrequencyDatePicker(
            preselectedFrequencyDate = FrequencyDate.parse(date = selectedDate.value),
            onDateSelected = { date ->
                onValueChange(date.toString())
                selectedDate.value = date.toString()
            }
        )
    }
}

@Composable
private fun YearlyFrequencyPicker(
    selectedValue: String,
    frequencyNote: String,
    horizontalPadding: Dp,
    onValueChange: (String) -> Unit
) {
    V_M_Space()

    Column(
        modifier = Modifier
            .fillMaxWidth()
            .padding(horizontal = horizontalPadding)
    ) {
        Text(
            text = frequencyNote,
            style = MaterialTheme.typography.bodySmall
        )
        V_S_Space()
        DayAndMonthPicker(modifier = Modifier.fillMaxWidth(),
            preselectedFrequencyDate = FrequencyDate.parse(date = selectedValue),
            onDateSelected = { day, month ->
                val date = FrequencyDate(day = day, month = month, year = 0)
                onValueChange(date.toDayMonthString())
            })
    }
}