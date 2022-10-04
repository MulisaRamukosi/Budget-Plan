package com.puzzle.industries.budgetplan.components.picker

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Event
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R

@Composable
fun DayOfMonthPicker(modifier: Modifier = Modifier, day: Int, onClick: (Int) -> Unit) {

    ValueDropDownPicker(
        modifier = modifier,
        label = stringResource(id = R.string.day),
        leadingIcon = Icons.Rounded.Event,
        leadingIconDescription = stringResource(id = R.string.desc_budget_plan_day_icon),
        currentValue = day,
        values = (1..31).toList(),
        onValueChange = onClick
    )
}