@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.picker

import android.app.DatePickerDialog
import android.content.Context
import android.widget.Toast
import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.interaction.PressInteraction
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.domain.models.FrequencyDate
import java.util.*

@Composable
fun FrequencyDatePicker(
    preselectedFrequencyDate: FrequencyDate,
    onDateSelected: (FrequencyDate) -> Unit
) {
    val source = remember { MutableInteractionSource() }
    val pressedState = source.interactions.collectAsState(
        initial = PressInteraction.Cancel(PressInteraction.Press(Offset.Zero))
    )

    val context: Context = LocalContext.current
    val datePickerError: String = stringResource(id = R.string.error_frequency_date_picker)
    val datePickerDialog: DatePickerDialog = datePickerDialog(
        day = preselectedFrequencyDate.day,
        month = preselectedFrequencyDate.month,
        year = preselectedFrequencyDate.year,
        onDateSelected = { selectedDay, selectedMonth, selectedYear ->
            val newDate = FrequencyDate(
                day = selectedDay,
                month = selectedMonth,
                year = selectedYear
            )
            if (newDate.year >= preselectedFrequencyDate.year && newDate.month >= preselectedFrequencyDate.month
                && (newDate.day >= preselectedFrequencyDate.day || (newDate.month > preselectedFrequencyDate.month))
            ) {
                onDateSelected(newDate)
            }
            else {
                Toast.makeText(context, datePickerError, Toast.LENGTH_LONG).show()
            }
        }
    )

    if (pressedState.value is PressInteraction.Release) {
        datePickerDialog.show()
        source.tryEmit(PressInteraction.Cancel(PressInteraction.Press(Offset.Zero)))
    }

    OutlinedTextField(
        modifier = Modifier.fillMaxWidth(),
        leadingIcon = {
            Icon(
                imageVector = Icons.Rounded.Event,
                contentDescription = stringResource(id = R.string.desc_date_icon)
            )
        },
        label = { Text(text = stringResource(id = R.string.date)) },
        value = preselectedFrequencyDate.toString(),
        onValueChange = {},
        readOnly = true,
        interactionSource = source
    )

}

