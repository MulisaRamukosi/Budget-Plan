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
import com.puzzle.industries.budgetplan.components.spacer.H_M_Space
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.symbols.BulletPoint
import com.puzzle.industries.budgetplan.components.text.HomeCardTitle
import com.puzzle.industries.budgetplan.components.text.SingleLineText
import com.puzzle.industries.budgetplan.data.stats.StatItem
import com.puzzle.industries.budgetplan.previewProviders.providers.DonutChartPreviewProvider
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing



@Composable
fun DonutChart(modifier: Modifier, title: String, values: List<StatItem>) {
    val chartItemValues = values.map { it.value }

    val percentages = remember { convertValuesToPercentage(values = chartItemValues) }
    val titles = remember { values.map { it.key.title } }
    val angles = remember { convertPercentagesToAngles(percentages = percentages) }
    val colors = remember { values.map { it.key.color } }

    Card(modifier = modifier) {
        Column(modifier = Modifier.fillMaxWidth().padding(all = MaterialTheme.spacing.medium)) {
            HomeCardTitle(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = title
            )

            V_M_Space()

            Row(
                modifier = Modifier.fillMaxWidth(),
                verticalAlignment = Alignment.CenterVertically,
                horizontalArrangement = Arrangement.Center
            ) {
                DrawDonutChart(
                    modifier = Modifier.size(size = 80.dp),
                    angles = angles,
                    colors = colors
                )

                H_M_Space()

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
        val gap = if(colors.size == 1 || angles.contains(360f)) 0 else (8 * 2)/colors.size

        angles.forEachIndexed { index, angle ->
            val sweepAngle = if (angle == 0f) 0f else angle - gap

            if(sweepAngle > 0){
                drawArc(
                    color = colors[index],
                    startAngle = startAngle,
                    sweepAngle = sweepAngle,
                    useCenter = false,
                    size = Size(width = donutRadius, height = donutRadius),
                    topLeft = Offset(
                        x = (canvasSize / 2) - (donutRadius / 2),
                        y = canvasSize / 2 - (donutRadius / 2)
                    ),
                    style = Stroke(width = donutRadius / 5)
                )
            }

            startAngle += angle
        }
    }

}

private fun convertValuesToPercentage(values: List<Double>): List<Float> {
    val sum = values.sum().toFloat()
    val total = if(sum == 0f) 1f else sum
    return values.map { it.toFloat() * 100 / total }
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

                H_S_Space()

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
            modifier = Modifier,
            title = "Current Month Expenses",
            values = values
        )
    }
}