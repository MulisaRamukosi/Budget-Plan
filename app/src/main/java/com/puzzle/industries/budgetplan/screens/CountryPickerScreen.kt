package com.puzzle.industries.budgetplan.screens

import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.itemsIndexed
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.data.CountryCurrency
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.CurrencyViewModel

@Composable
fun CountryPickerScreen(
    viewModel: CurrencyViewModel,
    onDoneClick: () -> Unit
) {
    val selectedIndex by viewModel.sub.observeAsState()

    Column {
        Text(
            text = stringResource(id = R.string.select_currency),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(all = MaterialTheme.spacing.medium)
        )

        LazyColumn(modifier = Modifier.fillMaxWidth()) {

            itemsIndexed(items = viewModel.countries) { index, item ->
                CountryCurrencyItem(
                    modifier = Modifier.fillMaxWidth(),
                    countryCurrency = item,
                    isSelected = index == selectedIndex,
                    onClick = { viewModel.pub.value = index }
                )
            }
        }

        Button(modifier = Modifier.padding(all = MaterialTheme.spacing.medium), onClick = onDoneClick) {
            Text(text = stringResource(id = R.string.done))
        }
    }


}

@Composable
private fun CountryCurrencyItem(
    modifier: Modifier,
    countryCurrency: CountryCurrency,
    isSelected: Boolean,
    onClick: () -> Unit
) {
    Box(
        modifier = modifier
            .background(color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent)
            .clickable { onClick() }
    ) {
        Row(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MaterialTheme.spacing.small),
            verticalAlignment = Alignment.CenterVertically
        ) {
            Image(
                modifier = Modifier
                    .size(width = 40.dp, height = 30.dp)
                    .padding(all = MaterialTheme.spacing.extraSmall)
                    .clip(shape = RoundedCornerShape(size = MaterialTheme.spacing.small)),
                painter = painterResource(id = countryCurrency.flagId),
                contentDescription = countryCurrency.country
            )

            H_S_Space()

            Text(text = "${countryCurrency.currency} - ${countryCurrency.country}")
        }
    }

}

@Preview(showBackground = true)
@Composable
private fun PreviewCountryPickerScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        CountryPickerScreen(viewModel = viewModel(), onDoneClick = {})
    }
}