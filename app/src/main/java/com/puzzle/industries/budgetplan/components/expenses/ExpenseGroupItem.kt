@file:OptIn(
    ExperimentalMaterial3Api::class,
    ExperimentalMaterial3WindowSizeClassApi::class,
    ExperimentalAnimationApi::class
)

package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.animation.ExperimentalAnimationApi
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Event
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.*
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.MiniCaption
import com.puzzle.industries.budgetplan.components.ModifiableItemWrapper
import com.puzzle.industries.budgetplan.components.TitleAndDescription
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.spacing


@Composable
@ExperimentalAnimationApi
fun ExpenseGroupItem() {
    /*var expanded by remember { mutableStateOf(false) }
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
    }*/

    ExpenseGroup(
        title = "Expense Title",
        description = "some description",
        amount = 5000.0,
        numOfExpenses = 12
    )
}

@Composable
private fun ExpenseGroup(
    title: String,
    description: String,
    amount: Double,
    numOfExpenses: Int
) {
    ModifiableItemWrapper(modifier = Modifier.fillMaxWidth()) {
        Column {
            Column(modifier = it) {

                TitleAndDescription(title = title, description = description)

                Spacer(modifier = Modifier.height(height = MaterialTheme.spacing.medium))

                OutcomeAmount(amount = amount)

            }

            ExpenseOptions(modifier = Modifier.padding(start = MaterialTheme.spacing.medium))

            MiniCaption(
                modifier = it,
                imageVector = Icons.Rounded.Payments,
                message = stringResource(id = R.string.num_of_expenses, numOfExpenses)
            )
        }

    }
}

@Composable
private fun ExpenseOptions(modifier: Modifier){
    val chipRowScrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(state = chipRowScrollState)

    ) {
        AssistChip(
            label = { Text(text = stringResource(id = R.string.view_expenses)) },
            onClick = {}
        )

        Spacer(modifier = Modifier.width(width = MaterialTheme.spacing.small))

        AssistChip(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.desc_add_icon)
                )
            },
            label = { Text(text = stringResource(id = R.string.add_expense)) },
            onClick = {}
        )
    }
}

@Preview
@Composable
fun PreviewExpenseGroupItem() {
    BudgetPlanTheme(dynamicColor = false) {
        ExpenseGroupItem()
    }

}