package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.animation.AnimatedVisibility
import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.animation.core.Animatable
import androidx.compose.animation.core.tween
import androidx.compose.animation.expandVertically
import androidx.compose.animation.shrinkVertically
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.ExpandMore
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.*
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextOverflow
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.divider.HorizontalDivider
import com.puzzle.industries.budgetplan.ext.applyAnimation
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing
import kotlinx.coroutines.async


@Composable
@ExperimentalAnimationApi
fun ExpenseGroupItem() {
    var expanded by remember { mutableStateOf(false) }
    var arrowRotation by remember { mutableStateOf(0f) }
    var itemPadding by remember { mutableStateOf(0.dp.value) }
    val itemTargetPadding = MaterialTheme.spacing.medium.value

    val arrowRotationAnim = remember { Animatable(arrowRotation) }
    val itemPaddingAnim = remember { Animatable(itemPadding) }

    LaunchedEffect(key1 = expanded) {
        val arrowAnim = async {
            arrowRotationAnim.applyAnimation(
                targetValue = if (expanded) 180f else 0f,
                durationMillis = 500
            ) {
                arrowRotation = it
            }
        }

        val paddingAnim = async {
            itemPaddingAnim.applyAnimation(
                targetValue = if (expanded) itemTargetPadding else 0.dp.value,
                durationMillis = 500
            ) {
                itemPadding = it
            }
        }

        arrowAnim.await()
        paddingAnim.await()
    }

    ExpenseGroup(itemPadding, expanded, arrowRotation, onExpandClick = { expanded = !expanded })
}

@Composable
private fun ExpenseGroup(
    itemPadding: Float,
    expanded: Boolean,
    arrowRotation: Float,
    onExpandClick: () -> Unit = {}
) {
    Surface(modifier = Modifier.padding(all = itemPadding.dp), shape = MaterialTheme.shapes.medium) {
        Column {
            Row(
                modifier = Modifier.padding(
                    all = MaterialTheme.spacing.medium
                ),
                verticalAlignment = Alignment.CenterVertically,
            ) {
                Icon(
                    modifier = Modifier
                        .clickable {
                            onExpandClick()
                        }
                        .rotate(arrowRotation),
                    imageVector = Icons.Rounded.ExpandMore,
                    contentDescription = stringResource(id = R.string.desc_expenses_toggle)
                )

                Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))

                Column(modifier = Modifier.weight(weight = 1f)) {
                    Text(
                        text = "Expense Title",
                        maxLines = 1,
                        style = MaterialTheme.typography.titleLarge,
                        fontWeight = FontWeight.Medium,
                        overflow = TextOverflow.Ellipsis
                    )

                    Text(
                        text = "total expenses: 5",
                        style = MaterialTheme.typography.bodySmall
                    )
                }


                Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.medium))

                Text(
                    modifier = Modifier.align(alignment = Alignment.Top),
                    text = "R13",
                    color = MaterialTheme.colorScheme.primary,
                    style = MaterialTheme.typography.bodyLarge,
                    fontWeight = FontWeight.Bold
                )
            }

            AnimatedVisibility(
                visible = expanded,
                enter = expandVertically(expandFrom = Alignment.Top) { 0 },
                exit = shrinkVertically(animationSpec = tween()) { fullHeight -> fullHeight / 2 },
            ) {
                HorizontalDivider(modifier = Modifier.padding(horizontal = MaterialTheme.spacing.small))

                Column {
                    repeat(5) {
                        ExpenseItem(modifier = Modifier.padding(all = MaterialTheme.spacing.medium))
                    }
                }
            }
        }
    }
}

@Preview
@Composable
@ExperimentalAnimationApi
@ExperimentalMaterial3WindowSizeClassApi
fun PreviewExpenseGroupItem() {
    BudgetPlanTheme(dynamicColor = false) {
        ExpenseGroupItem()
    }

}