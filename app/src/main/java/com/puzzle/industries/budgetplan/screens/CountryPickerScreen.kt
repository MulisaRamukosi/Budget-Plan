@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.screens

import android.content.res.Configuration.UI_MODE_NIGHT_YES
import androidx.compose.foundation.Image
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.lazy.rememberLazyListState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Search
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.constraintlayout.compose.ConstrainedLayoutReference
import androidx.constraintlayout.compose.ConstraintLayout
import androidx.constraintlayout.compose.ConstraintLayoutScope
import androidx.constraintlayout.compose.Dimension
import androidx.lifecycle.viewmodel.compose.viewModel
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

    ConstraintLayout(modifier = Modifier.fillMaxSize()) {
        val (searchBarConstraint, countriesConstraint, doneBtnConstraint) = createRefs()

        searchBarSection(
            countryCurrencyViewModel = countryCurrencyViewModel,
            constraint = searchBarConstraint
        )()

        doneButtonSection(
            constraint = doneBtnConstraint,
            onDoneClick = onDoneClick
        )()

        countriesSection(
            countryCurrencyViewModel = countryCurrencyViewModel,
            topConstraint = searchBarConstraint,
            middleConstraint = countriesConstraint,
            bottomConstraint = doneBtnConstraint
        )()
    }

}

@Composable
private fun searchBarSection(
    countryCurrencyViewModel: CountryCurrencyViewModel,
    constraint: ConstrainedLayoutReference
): @Composable ConstraintLayoutScope.() -> Unit = {

    val filterText by countryCurrencyViewModel.filterText.valueStateFlow.collectAsState()

    Column(modifier = Modifier.constrainAs(ref = constraint) {
        linkTo(start = parent.start, end = parent.end)
        top.linkTo(anchor = parent.top)
    }) {
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
            onValueChange = {
                countryCurrencyViewModel.filterText.onValueChange(it)
            }
        )
    }
}

@Composable
private fun doneButtonSection(
    constraint: ConstrainedLayoutReference,
    onDoneClick: () -> Unit
): @Composable ConstraintLayoutScope.() -> Unit = {

    Row(
        modifier = Modifier
            .fillMaxWidth()
            .padding(all = MaterialTheme.spacing.medium)
            .constrainAs(ref = constraint) {
                linkTo(start = parent.start, end = parent.end)
                bottom.linkTo(anchor = parent.bottom)
            },
        content = { Button(onClick = onDoneClick, content = { Text(text = stringResource(id = R.string.done)) }) }
    )

}

@Composable
private fun countriesSection(
    countryCurrencyViewModel: CountryCurrencyViewModel,
    topConstraint: ConstrainedLayoutReference,
    middleConstraint: ConstrainedLayoutReference,
    bottomConstraint: ConstrainedLayoutReference
): @Composable ConstraintLayoutScope.() -> Unit = {

    val selectedCountryCurrency by countryCurrencyViewModel.sub.collectAsState()
    val countries by countryCurrencyViewModel.allCountries.collectAsState()

    LazyColumn(
        modifier = Modifier
            .constrainAs(ref = middleConstraint) {
                start.linkTo(parent.start)
                end.linkTo(parent.end)
                top.linkTo(topConstraint.bottom)
                bottom.linkTo(bottomConstraint.top)
                height = Dimension.fillToConstraints
                width = Dimension.fillToConstraints
            }
    ) {

        items(items = countries) { item: CountryCurrency ->
            CountryCurrencyItem(
                modifier = Modifier.fillMaxWidth(),
                countryCurrency = item,
                isSelected = item == selectedCountryCurrency,
                onClick = { countryCurrencyViewModel.publishValue(value = item) }
            )
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