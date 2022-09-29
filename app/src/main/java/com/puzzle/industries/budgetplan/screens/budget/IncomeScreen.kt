@file:OptIn(ExperimentalMaterial3Api::class)

package com.puzzle.industries.budgetplan.screens.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.income.IncomeItem
import com.puzzle.industries.budgetplan.components.income.TotalIncome
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.budget.IncomeViewModel
import com.puzzle.industries.domain.models.income.Income

@Composable
fun IncomeScreen(
    viewModel: IncomeViewModel,
    onEditItemClick: (Income) -> Unit,
    onAddIncomeClick: () -> Unit
) {

    val currencySymbol by viewModel.currencySymbol.collectAsState()

    Box(modifier = Modifier.fillMaxSize()) {

        Column {
            TotalIncome(
                modifier = Modifier
                    .padding(all = MaterialTheme.spacing.medium)
                    .align(Alignment.End),
                income = viewModel.getTotalIncome()
            )

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .weight(1f),
                contentPadding = PaddingValues(bottom = MaterialTheme.spacing.large * 2)
            ) {
                items(items = viewModel.incomes.value) { income ->
                    IncomeItem(
                        income = income,
                        onEditClick = onEditItemClick,
                        onDeleteClick = {}
                    )
                    Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))
                }
            }

        }

        Button(
            onClick = onAddIncomeClick,
            modifier = Modifier
                .align(alignment = Alignment.BottomCenter)
                .padding(all = MaterialTheme.spacing.medium)
        ) {
            Text(text = stringResource(id = R.string.add_income))
        }
    }
}
