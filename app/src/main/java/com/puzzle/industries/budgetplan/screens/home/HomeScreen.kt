package com.puzzle.industries.budgetplan.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.BudgetPlanHeader
import com.puzzle.industries.budgetplan.components.expenses.PendingExpenses
import com.puzzle.industries.budgetplan.components.layout.StaggeredVerticalGrid
import com.puzzle.industries.budgetplan.components.reminder.PaymentReminders
import com.puzzle.industries.budgetplan.components.stats.BarChart
import com.puzzle.industries.budgetplan.components.stats.BarGroup
import com.puzzle.industries.budgetplan.components.stats.DonutChart
import com.puzzle.industries.budgetplan.data.stats.Key
import com.puzzle.industries.budgetplan.data.stats.StatItem
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.colorPickerColors
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.ExpenseViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.income.IncomeViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.reminder.ReminderViewModel
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses

@Composable
fun HomeScreen(
    incomeViewModel: IncomeViewModel,
    reminderViewModel: ReminderViewModel,
    expenseViewModel: ExpenseViewModel
) {
    val itemModifier = Modifier
        .fillMaxWidth()
        .padding(all = MaterialTheme.spacing.small)

    StaggeredVerticalGrid(
        modifier = Modifier
            .verticalScroll(state = rememberScrollState())
            .padding(all = MaterialTheme.spacing.small),
        maxColumnWidth = 500.dp
    ) {
        BudgetPlanHeader(
            modifier = itemModifier,
            incomeViewModel = incomeViewModel,
            expenseViewModel = expenseViewModel
        )
        PendingExpenses(modifier = itemModifier)
        PaymentReminders(modifier = itemModifier, reminderViewModel = reminderViewModel)
        ExpenseGroupDonutChartField(modifier = itemModifier, expenseViewModel = expenseViewModel)

        BarChart(
            modifier = itemModifier,
            title = "dummy",
            values = dummyGroup()
        )
    }


}

@Composable
fun ExpenseGroupDonutChartField(modifier: Modifier, expenseViewModel: ExpenseViewModel) {
    val expenseGroupsWithExpenses by expenseViewModel.expenseGroupsWithExpenses.collectAsState()

    ExpenseGroupDonutChartItem(
        modifier = modifier,
        expenseGroupsWithExpenses = expenseGroupsWithExpenses
    )
}

@Composable
private fun ExpenseGroupDonutChartItem(
    modifier: Modifier,
    expenseGroupsWithExpenses: List<ExpenseGroupWithExpenses>
) {


    if (expenseGroupsWithExpenses.isNotEmpty()) {
        val donutStats = expenseGroupsWithExpenses.map {
            StatItem(
                key = Key(
                    title = it.expenseGroup.name,
                    color = MaterialTheme.colorPickerColors.getColor(it.expenseGroup.colorId)
                ),
                value = it.expenses.sumOf { expense -> expense.amount }
            )
        }.toList()

        DonutChart(
            modifier = modifier,
            title = stringResource(id = R.string.expense_categories_share),
            values = donutStats
        )
    }

}

@Composable
private fun dummyGroup() = listOf(
    BarGroup(
        title = "Jan",
        values = listOf(
            StatItem(
                key = Key(
                    title = "Income",
                    color = MaterialTheme.colorScheme.inversePrimary,
                ),
                value = 100.0
            ),
            StatItem(
                key = Key(
                    title = "Outcome",
                    color = MaterialTheme.colorScheme.primary,
                ),
                value = 60.0
            )
        )
    ),
    BarGroup(
        title = "Feb",
        values = listOf(
            StatItem(
                key = Key(
                    title = "Income",
                    color = MaterialTheme.colorScheme.inversePrimary,
                ),
                value = 200.0
            ),
            StatItem(
                key = Key(
                    title = "Outcome",
                    color = MaterialTheme.colorScheme.primary,
                ),
                value = 80.0
            ),
            StatItem(
                key = Key(
                    title = "Missed payments",
                    color = MaterialTheme.colorScheme.error,
                ),
                value = 20.0
            ),
        )
    ),
    BarGroup(
        title = "Mar",
        values = listOf(
            StatItem(
                key = Key(
                    title = "Income",
                    color = MaterialTheme.colorScheme.inversePrimary,
                ),
                value = 200.0
            ),
            StatItem(
                key = Key(
                    title = "Outcome",
                    color = MaterialTheme.colorScheme.primary
                ),
                value = 60.0
            )
        )
    ),
    BarGroup(
        title = "Apr",
        values = listOf(
            StatItem(
                key = Key(
                    title = "Income",
                    color = MaterialTheme.colorScheme.inversePrimary
                ),
                value = 200.0
            ),
            StatItem(
                key = Key(
                    title = "Outcome",
                    color = MaterialTheme.colorScheme.primary
                ),
                value = 30.0
            ),
            StatItem(
                key = Key(
                    title = "Missed payments",
                    color = MaterialTheme.colorScheme.error,
                ),
                value = 30.0
            )
        )
    ),
    BarGroup(
        title = "May",
        values = listOf(
            StatItem(
                key = Key(
                    title = "Income",
                    color = MaterialTheme.colorScheme.inversePrimary
                ),
                value = 180.0

            ),
            StatItem(
                key = Key(
                    title = "Outcome",
                    color = MaterialTheme.colorScheme.primary
                ),
                value = 100.0
            )
        )
    ),
)

@Preview
@Composable
fun ExpenseGroupDonutChartField() {
    BudgetPlanTheme {
        ExpenseGroupDonutChartItem(
            modifier = Modifier.fillMaxWidth(), expenseGroupsWithExpenses = listOf(
                ExpenseGroupWithExpenses(
                    expenseGroup = ExpenseGroup(
                        name = "test",
                        description = "",
                        colorId = "color0"
                    ),
                    expenses = emptyList()
                )
            )
        )
    }
}
