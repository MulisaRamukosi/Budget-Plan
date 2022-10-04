package com.puzzle.industries.budgetplan.components.picker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R

@Composable
fun MonthPicker(modifier: Modifier = Modifier, month: Int, onClick: (Int) -> Unit) {

    val months = stringArrayResource(id = R.array.months).asList()

    ValueDropDownPicker(
        modifier = modifier,
        label = stringResource(id = R.string.month),
        leadingIcon = Icons.Rounded.Event,
        leadingIconDescription = stringResource(id = R.string.desc_date_icon),
        currentValue = months[month],
        values = months,
        onValueChange = {
            val index = months.indexOf(it)
            onClick(index)
        }
    )
}