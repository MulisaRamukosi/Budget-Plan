package com.puzzle.industries.budgetplan.components.picker

import android.app.DatePickerDialog
import android.provider.CalendarContract.Calendars
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import java.util.*

@Composable
fun datePickerDialog(
    day: Int,
    month: Int,
    year: Int,
    disablePreviousDays: Boolean,
    onDateSelected: (day: Int, month: Int, year: Int) -> Unit
): DatePickerDialog {
    val calendar: Calendar = Calendar.getInstance()
    val currentDayInMillis = calendar.timeInMillis

    calendar.set(year, month, day)

    val datePickerDialog = DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            onDateSelected(selectedDay, selectedMonth, selectedYear)
        }, year, month, day
    )

    if (disablePreviousDays) datePickerDialog.datePicker.minDate = currentDayInMillis

    return datePickerDialog
}