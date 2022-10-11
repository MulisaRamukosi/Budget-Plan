@file:OptIn(
    ExperimentalMaterial3Api::class, ExperimentalAnimationApi::class
)

package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.animation.*
import androidx.compose.foundation.horizontalScroll
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.rememberScrollState
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Add
import androidx.compose.material.icons.rounded.Payments
import androidx.compose.material3.*
import androidx.compose.runtime.*
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.TransformOrigin
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.layout.MiniCaption
import com.puzzle.industries.budgetplan.components.layout.ModifiableItemWrapper
import com.puzzle.industries.budgetplan.components.layout.TitleAndDescription
import com.puzzle.industries.budgetplan.components.spacer.H_S_Space
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.components.symbols.BulletPoint
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.colorPickerColors
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses
import java.util.*

@Composable
fun ExpenseGroupItem(
    currencySymbol: String,
    expenseGroupWithExpenses: ExpenseGroupWithExpenses,
    onAddExpenseClick: () -> Unit,
    onEditExpenseGroupClick: (ExpenseGroup) -> Unit,
    onDeleteExpenseGroupClick: (ExpenseGroupWithExpenses) -> Unit,
    onEditExpenseClick: (Expense) -> Unit,
    onDeleteExpenseClick: (Expense) -> Unit
) {
    var showExpenses by rememberSaveable { mutableStateOf(false) }
    val expenseGroup = expenseGroupWithExpenses.expenseGroup
    val expenses = expenseGroupWithExpenses.expenses

    val totalExpenseAmount = expenses.sumOf { expense -> expense.amount }

    Column {
        ModifiableItemWrapper(
            modifier = Modifier.fillMaxWidth(),
            onEditClick = { onEditExpenseGroupClick(expenseGroup) },
            onDeleteClick = { onDeleteExpenseGroupClick(expenseGroupWithExpenses) }
        ) {
            Column {
                Column(modifier = it) {
                    BulletPoint(
                        color = MaterialTheme.colorPickerColors.getColor(expenseGroup.colorId),
                        size = 8.dp
                    )

                    TitleAndDescription(
                        title = expenseGroup.name,
                        description = expenseGroup.description
                    )

                    V_M_Space()

                    OutcomeAmount(currencySymbol = currencySymbol, amount = totalExpenseAmount)
                }

                ExpenseOptions(
                    modifier = Modifier.padding(start = MaterialTheme.spacing.medium),
                    showExpenses = showExpenses,
                    enableShowExpensesOption = expenses.isEmpty().not(),
                    onViewExpenseClicked = {
                        showExpenses = !showExpenses
                    },
                    onAddExpense = onAddExpenseClick
                )

                MiniCaption(
                    modifier = it,
                    imageVector = Icons.Rounded.Payments,
                    message = stringResource(id = R.string.num_of_expenses, expenses.size)
                )
            }
        }

        ExpenseItems(
            modifier = Modifier.fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium),
            showExpenses = showExpenses,
            expenses = expenses,
            currencySymbol = currencySymbol,
            onEditExpenseClick = onEditExpenseClick,
            onDeleteExpenseClick = onDeleteExpenseClick
        )
    }
}

@Composable
private fun ExpenseItems(
    modifier: Modifier,
    showExpenses: Boolean,
    expenses: List<Expense>,
    currencySymbol: String,
    onEditExpenseClick: (Expense) -> Unit,
    onDeleteExpenseClick: (Expense) -> Unit
) {
    AnimatedVisibility(
        visible = showExpenses,
        enter = slideInVertically()
                + expandVertically(expandFrom = Alignment.Top)
                + scaleIn(transformOrigin = TransformOrigin(0.5f, 0f))
                + fadeIn(initialAlpha = 0.3f),
        exit = slideOutVertically(targetOffsetY = {40})
                + shrinkVertically(shrinkTowards = Alignment.Top)
                + fadeOut()
                + scaleOut(targetScale = 1.2f)
    ) {
        Column(
            modifier = modifier
        ) {
            expenses.forEach { expense ->
                V_S_Space()
                ExpenseItem(
                    modifier = Modifier.fillMaxWidth(),
                    currencySymbol = currencySymbol,
                    expense = expense,
                    onEditClick = onEditExpenseClick,
                    onDeleteClick = onDeleteExpenseClick
                )
            }
        }
    }
}


@Composable
private fun ExpenseOptions(
    modifier: Modifier,
    showExpenses: Boolean,
    enableShowExpensesOption: Boolean,
    onViewExpenseClicked: () -> Unit,
    onAddExpense: () -> Unit
) {
    val chipRowScrollState = rememberScrollState()

    Row(
        modifier = modifier
            .horizontalScroll(state = chipRowScrollState)

    ) {
        AssistChip(
            label = {
                Text(
                    text = stringResource(
                        id = if (showExpenses) R.string.view_less_expenses
                        else R.string.view_more_expenses
                    )
                )
            },
            onClick = onViewExpenseClicked,
            enabled = enableShowExpensesOption
        )

        H_S_Space()

        AssistChip(
            leadingIcon = {
                Icon(
                    imageVector = Icons.Rounded.Add,
                    contentDescription = stringResource(id = R.string.desc_add_icon)
                )
            },
            label = { Text(text = stringResource(id = R.string.add_expense)) },
            onClick = onAddExpense
        )
    }
}

@Preview
@Composable
fun PreviewExpenseGroupItem() {
    BudgetPlanTheme(dynamicColor = false) {
        ExpenseGroupItem(
            currencySymbol = "R",
            expenseGroupWithExpenses = ExpenseGroupWithExpenses(
                expenseGroup = ExpenseGroup(name = "dummy title", description = "some desc", colorId = ""),
                expenses = listOf(
                    Expense(
                        expenseGroupId = UUID.randomUUID(),
                        name = "exp1",
                        amount = 12.0,
                        description = "some desc",
                        frequencyType = FrequencyType.MONTHLY,
                        frequencyWhen = "1"
                    ),
                    Expense(
                        expenseGroupId = UUID.randomUUID(),
                        name = "exp2",
                        amount = 14.0,
                        description = "some desc",
                        frequencyType = FrequencyType.MONTHLY,
                        frequencyWhen = "1"
                    )
                )
            ),
            onEditExpenseClick = {},
            onEditExpenseGroupClick = {},
            onDeleteExpenseClick = {},
            onDeleteExpenseGroupClick = {},
            onAddExpenseClick = {}
        )
    }

}