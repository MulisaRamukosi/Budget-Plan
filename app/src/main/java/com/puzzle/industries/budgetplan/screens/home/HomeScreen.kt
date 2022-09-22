package com.puzzle.industries.budgetplan.screens.home

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.components.BudgetPlanHeader
import com.puzzle.industries.budgetplan.components.StaggeredVerticalGrid
import com.puzzle.industries.budgetplan.components.expenses.PendingExpenses
import com.puzzle.industries.budgetplan.components.reminder.PaymentReminders
import com.puzzle.industries.budgetplan.components.stats.BarChart
import com.puzzle.industries.budgetplan.components.stats.BarGroup
import com.puzzle.industries.budgetplan.components.stats.DonutChart
import com.puzzle.industries.budgetplan.data.stats.Key
import com.puzzle.industries.budgetplan.data.stats.StatItem
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing

@Composable
fun HomeScreen() {
    val itemModifier = Modifier.fillMaxWidth().padding(all = MaterialTheme.spacing.small)
    StaggeredVerticalGrid(
        modifier = Modifier.verticalScroll(state = rememberScrollState()).padding(all = MaterialTheme.spacing.small),
        maxColumnWidth = 500.dp
    ) {
        BudgetPlanHeader(modifier = itemModifier)
        PendingExpenses(modifier = itemModifier)
        PaymentReminders(modifier = itemModifier)
        DonutChart(
            modifier = itemModifier,
            title = "Dummy",
            values = dummyDonut()
        )
        BarChart(
            modifier = itemModifier,
            title = "dummy",
            values = dummyGroup()
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

@Composable
private fun dummyDonut() = listOf(
    StatItem(
        key = Key(title = "Entertainment", color = Color(color = 0xFF103D61)),
        value = 70.0
    ),
    StatItem(
        key = Key(title = "Groceries", color = Color(color = 0xFF1F7947)),
        value = 20.0
    ),
    StatItem(
        key = Key(title = "HouseHold", color = Color(color = 0xFF9E9333)),
        value = 40.0
    ),
    StatItem(
        key = Key(title = "Family", color = Color(color = 0xFFA74343)),
        value = 40.0
    ),
)

@Preview(showBackground = true)
@Composable
fun PreviewHome() {
    BudgetPlanTheme(dynamicColor = false) {
        HomeScreen()
    }
}
