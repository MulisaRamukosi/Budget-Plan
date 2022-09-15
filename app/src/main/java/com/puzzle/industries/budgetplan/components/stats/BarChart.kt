package com.puzzle.industries.budgetplan.components.stats

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Card
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.symbols.BulletPoint
import com.puzzle.industries.budgetplan.components.divider.HorizontalDashedDivider
import com.puzzle.industries.budgetplan.components.spacer.*
import com.puzzle.industries.budgetplan.components.text.HomeCardTitle
import com.puzzle.industries.budgetplan.data.stats.Key
import com.puzzle.industries.budgetplan.data.stats.StatItem
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing


data class BarGroup(
    val title: String,
    val values: List<StatItem>
)

@Composable
fun BarChart(title: String, values: List<BarGroup>) {
    //TODO: Calculations to be moved to view model

    val barMaxHeight = 60.dp
    var selectedItem = remember { values.size - 1 }
    val titles = remember { values.map { barGroup -> barGroup.title } }
    val colors = remember { values.map { barGroup -> barGroup.values.map { it.key.color } } }
    val baseValues = remember { values.map { barGroup -> barGroup.values.map { it.value } } }
    val keys = remember { values.flatMap { it.values.map { statItem -> statItem.key } }.distinct() }
    val highestValue = remember {
        baseValues.fold(1.0) { acc, list ->
            if (list.max() > acc) list.max() else acc
        }
    }
    val barHeights = remember {
        baseValues.map {
            constraintListToHeight(
                targetHeight = barMaxHeight.value,
                highestValue = highestValue,
                values = it
            )
        }
    }

    Card {
        Column(modifier = Modifier.padding(all = MaterialTheme.spacing.medium)) {
            HomeCardTitle(
                modifier = Modifier.align(alignment = Alignment.CenterHorizontally),
                text = title
            )

            V_M_Space()

            BarGraph(
                titles = titles,
                barHeights = barHeights,
                barColors = colors,
                barMaxHeight = barMaxHeight.value,
                selectedItem = selectedItem
            )

            V_M_Space()

            GraphKeys(keys = keys)

            V_M_Space()

            SelectedGroupInfo(group = values[selectedItem])
        }
    }
}

@Composable
private fun GraphKeys(modifier: Modifier = Modifier, keys: List<Key>){
    Row(modifier = modifier){
        keys.forEach { key ->
            KeyInfo(key = key)

            H_M_Space()
        }
    }
}

@Composable
private fun KeyInfo(key: Key){
    Row (verticalAlignment = Alignment.CenterVertically){

        Bar(height = 10.dp.value, width = 4.dp.value, color = key.color)

        H_XS_Space()

        Text(text = key.title, style = MaterialTheme.typography.labelSmall)
    }
}

@Composable
private fun SelectedGroupInfo(modifier: Modifier = Modifier, group: BarGroup) {
    Column(modifier = modifier) {
        Text(text = group.title, style = MaterialTheme.typography.labelLarge)

        V_S_Space()

        Row(verticalAlignment = Alignment.CenterVertically) {
            group.values.forEach { value ->

                BulletPoint(color = value.key.color, size = 6.dp)

                H_S_Space()

                Text(text = stringResource(id = R.string.currency_amount, "R", value.value))

                H_M_Space()
            }
        }
    }
}

@Composable
private fun BarGraph(
    titles: List<String>,
    barHeights: List<List<Float>>,
    barColors: List<List<Color>>,
    barMaxHeight: Float,
    selectedItem: Int
) {
    Box(modifier = Modifier.fillMaxWidth()) {
        HorizontalDashedDivider(
            modifier = Modifier
                .fillMaxWidth()
                .padding(top = barMaxHeight.dp)
            ,
            thickness = 1.dp,
            color = MaterialTheme.colorScheme.outline
        )

        Row(modifier = Modifier.fillMaxWidth(), horizontalArrangement = Arrangement.SpaceEvenly) {
            titles.forEachIndexed { index, title ->
                BarGroupBars(
                    title = title,
                    maxBarHeight = barMaxHeight,
                    values = barHeights[index],
                    colors = barColors[index],
                    isSelected = index == selectedItem
                )
            }
        }

    }
}

@Composable
private fun BarGroupBars(
    title: String,
    maxBarHeight: Float,
    values: List<Float>,
    colors: List<Color>,
    isSelected: Boolean
) {
    Column(horizontalAlignment = Alignment.CenterHorizontally) {
        Row(
            modifier = Modifier.height(height = maxBarHeight.dp),
            verticalAlignment = Alignment.Bottom,
            horizontalArrangement = Arrangement.Center
        ) {
            H_XS_Space()

            values.forEachIndexed { index, value ->
                Bar(height = value, color = colors[index])
                H_XS_Space()
            }

        }

        V_XS_Space()

        Text(text = title, style = MaterialTheme.typography.labelSmall)

        if (isSelected) {
            V_XS_Space()

            SelectionIndicator(modifier = Modifier.width(width = 20.dp))
        }
    }
}

@Composable
private fun SelectionIndicator(modifier: Modifier) {
    Box(
        modifier = modifier
            .height(height = 4.dp)
            .clip(shape = RoundedCornerShape(size = 2.dp))
            .background(color = MaterialTheme.colorScheme.tertiary)
    )
}

@Composable
private fun Bar(height: Float, width: Float = 6.dp.value, cornerRadius: Dp = 2.dp, color: Color) {
    Box(
        modifier = Modifier
            .height(height = height.dp)
            .clip(shape = RoundedCornerShape(size = cornerRadius))
            .width(width = width.dp)
            .background(color = color)
    )
}

private fun constraintListToHeight(
    targetHeight: Float,
    highestValue: Double,
    values: List<Double>
): List<Float> {
    return values.map { targetHeight * (it.toFloat() / highestValue.toFloat()) }
}

@Preview
@Composable
@ExperimentalMaterial3WindowSizeClassApi
private fun PreviewBarChart() {
    BudgetPlanTheme(dynamicColor = false) {
        BarChart(
            title = "Income Outcome History(last 4 months)",
            values = listOf(
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
        )
    }
}
