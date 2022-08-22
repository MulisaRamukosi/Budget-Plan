package com.puzzle.industries.budgetplan.screens.budget

import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.income.IncomeItem
import com.puzzle.industries.budgetplan.components.income.TotalIncome
import com.puzzle.industries.budgetplan.previewProviders.providers.IncomeItemPreviewDataProvider
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.domain.models.IncomeDto
import java.util.*

@Composable
@ExperimentalMaterial3Api
fun IncomeScreen() {
    Content {}
}

@Composable
@ExperimentalMaterial3Api
private fun Content(
    incomes: List<IncomeDto> = emptyList(),
    onAddIncomeClick: () -> Unit
) {
    val totalIncome = incomes.sumOf { it.amount }

    Box(modifier = Modifier.fillMaxSize()) {

        Column {
            TotalIncome(
                modifier = Modifier
                    .padding(all = MaterialTheme.spacing.medium)
                    .align(Alignment.End),
                income = totalIncome
            )

            LazyColumn(
                modifier = Modifier
                    .padding(horizontal = MaterialTheme.spacing.medium)
                    .weight(1f),
                contentPadding = PaddingValues(bottom = MaterialTheme.spacing.large * 2)
            ) {
                items(items = incomes) { income ->
                    IncomeItem(income = income)
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

@Composable
@Preview(showBackground = true)
@ExperimentalMaterial3Api
@ExperimentalMaterial3WindowSizeClassApi

private fun IncomeScreenPreview(@PreviewParameter(IncomeItemPreviewDataProvider::class) incomes: List<IncomeDto>) {
    BudgetPlanTheme(dynamicColor = false) {
        Content(incomes = incomes) {

        }
    }
}
