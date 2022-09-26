package com.puzzle.industries.budgetplan.components.picker

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.puzzle.industries.domain.models.FrequencyDate
import java.util.*

@Composable
fun DatePicker(preselectedFrequencyDate: FrequencyDate?, onDateSelected: (FrequencyDate) -> Unit): DatePickerDialog {
    val context = LocalContext.current
    val calendar = Calendar.getInstance()

    val year = preselectedFrequencyDate?.year ?: calendar.get(Calendar.YEAR)
    val month = preselectedFrequencyDate?.month ?: calendar.get(Calendar.MONTH)
    val day = preselectedFrequencyDate?.day ?: calendar.get(Calendar.DAY_OF_MONTH)
    calendar.time = Date()

    return DatePickerDialog(
        context,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            onDateSelected(FrequencyDate(day = selectedDay, month = selectedMonth, year = selectedYear))
        }, year, month, day
    )
}