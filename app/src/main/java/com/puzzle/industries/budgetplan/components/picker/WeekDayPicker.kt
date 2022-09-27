@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.picker

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilterChip
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.Dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.domain.constants.WeekDays

@Composable
fun WeekDayPicker(
    modifier: Modifier = Modifier,
    selectedDay: WeekDays,
    horizontalPadding: Dp,
    onDaySelected: (WeekDays) -> Unit
) {
    val chipRowScrollState = rememberScrollState()
    Row(
        modifier = modifier
            .horizontalScroll(state = chipRowScrollState)
            .padding(horizontal = horizontalPadding)
    ) {
        val days = stringArrayResource(id = R.array.days)

        WeekDays.values().forEach {
            FilterChip(
                selected = selectedDay == it,
                label = { Text(text = days[it.ordinal]) },
                onClick = { if (it != selectedDay) onDaySelected(it) }
            )
            H_S_Space()
        }
    }
}