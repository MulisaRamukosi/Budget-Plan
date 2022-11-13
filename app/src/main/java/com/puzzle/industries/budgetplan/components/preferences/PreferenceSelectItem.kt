@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.components.preferences

import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.filled.Done
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.components.text.TitleAndDescription
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun <T> PreferenceSelectItem(
    modifier: Modifier = Modifier,
    title: String,
    description: String,
    selectedItem: T,
    values: List<T>,
    onOptionSelected: (selection: T) -> Unit
) {
    Card(modifier = modifier) {
        Column(modifier = Modifier.padding(vertical = MaterialTheme.spacing.medium)) {
            TitleAndDescription(
                modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
                title = title,
                description = description
            )

            V_S_Space()

            Options(
                selectedItem = selectedItem,
                values = values,
                onOptionSelected = onOptionSelected
            )


        }
    }
}

@Composable
private fun <T> Options(
    selectedItem: T,
    values: List<T>,
    onOptionSelected: (selection: T) -> Unit
) {
    val chipRowScrollState = rememberScrollState()
    var selectedValue by remember {
        mutableStateOf(selectedItem)
    }

    Row(
        modifier = Modifier
            .horizontalScroll(chipRowScrollState)
            .padding(start = MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium)
    ) {
        values.forEach {
            FilterChip(
                label = { Text(text = it.toString()) },
                selected = selectedValue == it,
                colors = FilterChipDefaults.filterChipColors(
                    selectedContainerColor = MaterialTheme.colorScheme.primary,
                    selectedLabelColor = MaterialTheme.colorScheme.onPrimary,
                    selectedLeadingIconColor = MaterialTheme.colorScheme.onPrimary
                ),
                onClick = {
                    selectedValue = it
                    onOptionSelected(it)
                },
                leadingIcon = if (selectedValue == it) {
                    {
                        Icon(
                            imageVector = Icons.Filled.Done,
                            contentDescription = stringResource(R.string.desc_done),
                            modifier = Modifier.size(FilterChipDefaults.IconSize)
                        )
                    }
                } else {
                    null
                }
            )
        }
    }
}

@Preview
@Composable
private fun PreviewPreferenceSelectItem() {
    BudgetPlanTheme(dynamicColor = false) {
        PreferenceSelectItem(
            title = "Theme",
            description = "some desc",
            selectedItem = "dummy",
            values = listOf("dsf", "sdf", "cvb", "fdg", "dummy", "dfgh"),
            onOptionSelected = {}
        )
    }
}