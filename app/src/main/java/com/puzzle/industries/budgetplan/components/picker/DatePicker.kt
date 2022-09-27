package com.puzzle.industries.budgetplan.components.picker

import android.app.DatePickerDialog
import android.widget.DatePicker
import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext

@Composable
fun datePickerDialog(
    day: Int,
    month: Int,
    year: Int,
    onDateSelected: (day: Int, month: Int, year: Int) -> Unit
): DatePickerDialog {
    return DatePickerDialog(
        LocalContext.current,
        { _: DatePicker, selectedYear: Int, selectedMonth: Int, selectedDay: Int ->
            onDateSelected(selectedDay, selectedMonth, selectedYear)
        }, year, month, day
    )
}