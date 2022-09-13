package com.puzzle.industries.budgetplan.components.stats

import androidx.compose.foundation.Canvas
import androidx.compose.foundation.layout.*
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.geometry.Offset
import androidx.compose.ui.geometry.Size
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.drawscope.Stroke
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.BulletPoint
import com.puzzle.industries.budgetplan.components.HomeCardTitle
import com.puzzle.industries.budgetplan.components.SingleLineText
import com.puzzle.industries.budgetplan.data.stats.StatItem
import com.puzzle.industries.budgetplan.previewProviders.providers.DonutChartPreviewProvider
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing



@Composable
fun DonutChart(title: String, values: List<StatItem>) {
    val chartItemValues = values.map { it.value }

    val percentages = remember { convertValuesToPercentage(values = chartItemValues) }
    val titles = remember { values.map { it.title } }
    val angles = remember { convertPercentagesToAngles(percentages = percentages) }
    val colors = remember { values.map { it.color } }

    Card {
        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.medium)) {
            HomeCardTitle(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = title
            )

            Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))

            Row(
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                DrawDonutChart(
                    modifier = Modifier.size(size = 80.dp),
                    angles = angles,
                    colors = colors
                )
                Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))
                ExpenseGroupsWithPercentage(
                    modifier = Modifier,
                    titles = titles,
                    percentages = percentages,
                    colors = colors
                )
            }
        }
    }
}

@Composable
private fun DrawDonutChart(
    modifier: Modifier,
    angles: List<Float>,
    colors: List<Color>
) {
    var startAngle = 270f
    Canvas(modifier = modifier) {
        val canvasSize = size.width
        val donutRadius = size.width / 1.25f
        val gap = if(colors.size == 1) 0 else (8 * 2)/colors.size

        angles.forEachIndexed { index, angle ->
            drawArc(
                color = colors[index],
                startAngle = startAngle,
                sweepAngle = angle - gap,
                useCenter = false,
                size = Size(width = donutRadius, height = donutRadius),
                topLeft = Offset(
                    x = (canvasSize / 2) - (donutRadius / 2),
                    y = canvasSize / 2 - (donutRadius / 2)
                ),
                style = Stroke(width = donutRadius / 5)
            )

            startAngle += angle
        }
    }

}

private fun convertValuesToPercentage(values: List<Double>): List<Float> {
    val sum = values.sum().toFloat()
    return values.map { it.toFloat() * 100 / sum }
}

private fun convertPercentagesToAngles(percentages: List<Float>): List<Float> {
    return percentages.map { 360f * it / 100 }
}

@Composable
private fun ExpenseGroupsWithPercentage(
    modifier: Modifier,
    titles: List<String>,
    percentages: List<Float>,
    colors: List<Color>
) {
    Column(modifier = modifier) {
        titles.forEachIndexed { index: Int, title: String ->

            val percentage = percentages[index]
            Row(verticalAlignment = Alignment.CenterVertically) {
                BulletPoint(color = colors[index])
                Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.small))
                SingleLineText(
                    text = stringResource(
                        id = R.string.pie_chart_key,
                        percentage,
                        title
                    ), style = MaterialTheme.typography.bodyMedium
                )
            }

        }
    }
}



@Preview
@Composable
@ExperimentalMaterial3WindowSizeClassApi
private fun PreviewExpenseGroupPieChart(@PreviewParameter(DonutChartPreviewProvider::class) values: List<StatItem>) {
    BudgetPlanTheme(dynamicColor = false) {
        DonutChart(
            title = "Current Month Expenses",
            values = values
        )
    }
}