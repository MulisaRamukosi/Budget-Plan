@file:OptIn(ExperimentalFoundationApi::class)

package com.puzzle.industries.budgetplan.screens.home

import androidx.compose.foundation.ExperimentalFoundationApi
import androidx.compose.foundation.Image
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.staggeredgrid.LazyVerticalStaggeredGrid
import androidx.compose.foundation.lazy.staggeredgrid.StaggeredGridCells
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ChevronRight
import androidx.compose.material3.Button
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.painterResource
import androidx.compose.ui.res.stringArrayResource
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import androidx.hilt.navigation.compose.hiltViewModel
import androidx.navigation.NavController
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.picker.DayOfMonthPicker
import com.puzzle.industries.budgetplan.components.preferences.PreferenceItem
import com.puzzle.industries.budgetplan.components.preferences.PreferenceSelectItem
import com.puzzle.industries.budgetplan.components.preferences.PreferenceSwitchItem
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.components.text.SingleLineText
import com.puzzle.industries.budgetplan.ext.GetOnceResult
import com.puzzle.industries.budgetplan.navigation.constants.RouteParamKey
import com.puzzle.industries.budgetplan.navigation.constants.Routes
import com.puzzle.industries.budgetplan.navigation.constants.ValueKey
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.configs.ViewConfig
import com.puzzle.industries.budgetplan.viewModels.SettingsViewModel
import com.puzzle.industries.domain.constants.ThemeType
import com.puzzle.industries.domain.models.CountryCurrency

@Composable
fun SettingsScreen(
    settingsViewModel: SettingsViewModel = hiltViewModel(),
    mainNavController: NavController
) {
    LazyVerticalStaggeredGrid(
        modifier = Modifier
            .fillMaxSize()
            .padding(top = MaterialTheme.spacing.medium)
            .padding(horizontal = MaterialTheme.spacing.medium),
        contentPadding = PaddingValues(bottom = MaterialTheme.spacing.medium),
        verticalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium),
        horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium),
        columns = StaggeredGridCells.Adaptive(minSize = ViewConfig.staggeredGridItemMinWidth),
    ) {
        item {
            ThemePreference(settingsViewModel = settingsViewModel)
        }

        item {
            HowItWorks(navController = mainNavController)
        }

        item {
            DebtPreference(settingsViewModel = settingsViewModel)
        }

        item {
            AutoDeleteExpenses(settingsViewModel = settingsViewModel)
        }

        item {
            CurrencyPreference(
                navController = mainNavController,
                settingsViewModel = settingsViewModel
            )
        }

        item {
            BudgetPlanGenerationDayPreference(settingsViewModel = settingsViewModel)
        }
    }
}

@Composable
private fun ThemePreference(settingsViewModel: SettingsViewModel) {
    val themeNames = stringArrayResource(id = R.array.themes).toList()
    val selectedTheme by settingsViewModel.selectedThemeOrdinal.collectAsState(initial = 0)

    PreferenceSelectItem(
        title = stringResource(id = R.string.theme),
        description = stringResource(id = R.string.desc_theme),
        selectedItem = themeNames[selectedTheme],
        values = themeNames,
        onOptionSelected = {
            val themeTypeOrdinal = themeNames.indexOf(it)
            val themeType = ThemeType.values()[themeTypeOrdinal]
            settingsViewModel.onThemeSelected(type = themeType)
        }
    )
}

@Composable
private fun DebtPreference(settingsViewModel: SettingsViewModel) {
    val allowDebt by settingsViewModel.allowDebtSelected.collectAsState(initial = false)

    PreferenceSwitchItem(
        title = stringResource(id = R.string.allow_debt),
        description = stringResource(id = R.string.desc_allow_debt),
        checked = allowDebt,
        onEnabledChanged = { settingsViewModel.onAllowDebtChange(allowDebt = it) }
    )
}

@Composable
private fun AutoDeleteExpenses(settingsViewModel: SettingsViewModel) {
    val autoDelete by settingsViewModel.autoDeleteReminders.collectAsState(initial = false)

    PreferenceSwitchItem(
        title = stringResource(id = R.string.auto_delete_expenses),
        description = stringResource(id = R.string.desc_auto_delete_expenses),
        checked = autoDelete,
        onEnabledChanged = {
            settingsViewModel.onAutoDeleteRemindersChange(autoDelete = it)
        }
    )
}

@Composable
private fun HowItWorks(navController: NavController) {
    PreferenceItem(
        title = stringResource(R.string.how_it_works),
        description = stringResource(id = R.string.desc_how_it_works)
    ) {
        Button(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            onClick = { /*TODO*/ }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {

                SingleLineText(
                    modifier = Modifier.weight(weight = 1f),
                    text = stringResource(id = R.string.read_more)
                )

                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = stringResource(id = R.string.desc_arrow_right_icon)
                )
            }
        }
    }
}

@Composable
private fun CurrencyPreference(
    navController: NavController,
    settingsViewModel: SettingsViewModel
) {
    val selectedCountryCurrency by settingsViewModel.selectedCurrency.collectAsState(initial = settingsViewModel.defaultCountryCurrency)

    SetupCurrencySelectionResponseHandler(
        navController = navController,
        settingsViewModel = settingsViewModel
    )

    PreferenceItem(
        title = stringResource(id = R.string.currency),
        description = stringResource(id = R.string.desc_currency)
    ) {
        Button(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            onClick = {
                navigateToCurrencySelectionScreen(
                    navController = navController,
                    countryCurrency = selectedCountryCurrency
                )
            }
        ) {
            Row(
                modifier = Modifier
                    .fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically
            ) {
                Image(
                    modifier = Modifier
                        .size(width = 40.dp, height = 30.dp)
                        .padding(all = MaterialTheme.spacing.extraSmall)
                        .clip(shape = RoundedCornerShape(size = MaterialTheme.spacing.small)),
                    painter = painterResource(id = selectedCountryCurrency.flagId),
                    contentDescription = selectedCountryCurrency.country
                )

                H_S_Space()

                SingleLineText(
                    modifier = Modifier.weight(weight = 1f),
                    text = "${selectedCountryCurrency.currency} - ${selectedCountryCurrency.country}"
                )

                Icon(
                    imageVector = Icons.Rounded.ChevronRight,
                    contentDescription = stringResource(id = R.string.desc_arrow_right_icon)
                )
            }
        }
    }
}

@Composable
private fun BudgetPlanGenerationDayPreference(settingsViewModel: SettingsViewModel) {
    val selectedDay by settingsViewModel.bPlanGenerationDay.collectAsState(initial = 1)

    PreferenceItem(
        title = stringResource(id = R.string.budget_plan_generation_day),
        description = stringResource(id = R.string.desc_budget_plan_generation_day)
    ) {
        DayOfMonthPicker(
            modifier = Modifier.padding(horizontal = MaterialTheme.spacing.medium),
            day = selectedDay
        ) { day -> settingsViewModel.onBudgetPlanGenerationDay(day = day) }
    }
}

@Composable
private fun SetupCurrencySelectionResponseHandler(
    navController: NavController,
    settingsViewModel: SettingsViewModel
) {
    navController.GetOnceResult<String>(
        keyResult = ValueKey.COUNTRY_CURRENCY_KEY.name,
        onResult = { currencyName ->
            settingsViewModel.onSelectedCurrencyChange(currencyName = currencyName)
        }
    )
}

private fun navigateToCurrencySelectionScreen(
    navController: NavController,
    countryCurrency: CountryCurrency
) {
    navController.navigate(
        route = Routes.CurrencyPicker.addParam(
            key = RouteParamKey.ID,
            value = countryCurrency.currency
        ).path
    )
}