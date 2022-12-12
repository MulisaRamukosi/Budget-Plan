@file:OptIn(ExperimentalMaterial3Api::class, ExperimentalCoilApi::class)

package com.puzzle.industries.budgetplan.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.painter.Painter
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.viewmodel.compose.viewModel
import coil.annotation.ExperimentalCoilApi
import coil.compose.rememberImagePainter
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.registrationFlow.CountryCurrencyViewModel
import com.puzzle.industries.domain.models.CountryCurrency

@Composable
fun CountryPickerScreen(
    countryCurrencyViewModel: CountryCurrencyViewModel,
    onDoneClick: () -> Unit
) {



    Column(modifier = Modifier.fillMaxSize()) {
        SearchBarSection(
            modifier = Modifier.fillMaxWidth(),
            countryCurrencyViewModel = countryCurrencyViewModel
        )

        CountriesSection(
            modifier = Modifier
                .fillMaxSize()
                .weight(weight = 1f, fill = true),
            countryCurrencyViewModel = countryCurrencyViewModel
        )

        DoneButtonSection(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MaterialTheme.spacing.medium),
            onDoneClick = onDoneClick
        )
    }
}

@Composable
private fun SearchBarSection(
    modifier: Modifier,
    countryCurrencyViewModel: CountryCurrencyViewModel
) {
    val filterText by countryCurrencyViewModel.filterText.valueStateFlow.collectAsState()

    Column(modifier = modifier) {
        Text(
            text = stringResource(id = R.string.select_currency),
            style = MaterialTheme.typography.titleMedium,
            modifier = Modifier.padding(all = MaterialTheme.spacing.medium)
        )
        OutlinedTextField(
            modifier = Modifier
                .fillMaxWidth()
                .padding(all = MaterialTheme.spacing.medium),
            value = filterText,
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Search, contentDescription = stringResource(
                        id = R.string.desc_search_icon
                    )
                )
            },
            placeholder = {
                Text(text = stringResource(id = R.string.search_country))
            },
            onValueChange = countryCurrencyViewModel.filterText.onValueChange
        )
    }
}

@Composable
private fun DoneButtonSection(
    modifier: Modifier,
    onDoneClick: () -> Unit
) {
    Row(
        modifier = modifier,
        content = {
            Button(
                onClick = onDoneClick,
                content = { Text(text = stringResource(id = R.string.done)) })
        }
    )
}

@Composable
private fun CountriesSection(
    modifier: Modifier,
    countryCurrencyViewModel: CountryCurrencyViewModel
) {
    val selectedCountryCurrency by countryCurrencyViewModel.sub.collectAsState()
    val countries by countryCurrencyViewModel.countries.collectAsState()

    LazyColumn(modifier = modifier) {

        items(items = countries, key = { it.country }) { item: CountryCurrency ->
            CountryCurrencyItem(
                modifier = Modifier.fillMaxWidth(),
                countryCurrency = item,
                isSelected = item == selectedCountryCurrency,
                onClick = countryCurrencyViewModel::publishValue
            )
        }
    }
}

@Composable
private fun CountryCurrencyItem(
    modifier: Modifier,
    countryCurrency: CountryCurrency,
    isSelected: Boolean,
    onClick: (CountryCurrency) -> Unit
) {
    val flagImage = rememberImagePainter(countryCurrency.flagId)

    Box(
        modifier = modifier
            .background(color = if (isSelected) MaterialTheme.colorScheme.surface else Color.Transparent)
            .clickable { onClick(countryCurrency) }
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
                painter = flagImage,
                contentDescription = countryCurrency.country
            )

            H_S_Space()

            Text(text = "${countryCurrency.currency} - ${countryCurrency.country}")
        }
    }

}

@Preview(
    showBackground = true,
    showSystemUi = true,
    uiMode = UI_MODE_NIGHT_YES
)
@Composable
private fun PreviewCountryPickerScreen() {
    BudgetPlanTheme(dynamicColor = false) {
        CountryPickerScreen(countryCurrencyViewModel = viewModel(), onDoneClick = {})
    }
}