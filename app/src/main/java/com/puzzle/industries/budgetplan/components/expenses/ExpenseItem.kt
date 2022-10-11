package com.puzzle.industries.budgetplan.components.expenses

import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.rounded.Alarm
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.windowsizeclass.ExperimentalMaterial3WindowSizeClassApi
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import com.puzzle.industries.budgetplan.components.layout.MiniCaption
import com.puzzle.industries.budgetplan.components.layout.ModifiableItemWrapper
import com.puzzle.industries.budgetplan.components.layout.TitleAndDescription
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.components.spacer.V_S_Space
import com.puzzle.industries.budgetplan.theme.BudgetPlanTheme
import com.puzzle.industries.budgetplan.theme.colorPickerColors
import com.puzzle.industries.budgetplan.util.buildFrequencyMessage
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.expense.Expense
import java.util.*

@Composable
fun ExpenseItem(
    modifier: Modifier,
    currencySymbol: String,
    expense: Expense,
    onEditClick: (Expense) -> Unit,
    onDeleteClick: (Expense) -> Unit
) {
    ModifiableItemWrapper(
        modifier = modifier,
        onEditClick = { onEditClick(expense) },
        onDeleteClick = { onDeleteClick(expense) }
    ) {
        Column(modifier = it) {

            TitleAndDescription(title = expense.name, description = expense.description)

            V_S_Space()

            OutcomeAmount(
                currencySymbol = currencySymbol,
                amountTextStyle = MaterialTheme.typography.bodyLarge,
                amount = expense.amount
            )

            V_M_Space()

            MiniCaption(
                imageVector = Icons.Rounded.Alarm, message = buildFrequencyMessage(
                    type = expense.frequencyType,
                    message = expense.frequencyWhen
                )
            )
        }
    }
}

@Composable
@Preview
@ExperimentalMaterial3WindowSizeClassApi
fun ExpenseItemPreview() {
    BudgetPlanTheme(dynamicColor = false) {
        ExpenseItem(
            modifier = Modifier.fillMaxWidth(),
            currencySymbol = "R",
            expense = Expense(
                expenseGroupId = UUID.randomUUID(),
                name = "some expense",
                amount = 124.0,
                description = "some desc",
                frequencyType = FrequencyType.MONTHLY,
                frequencyWhen = "1"
            ),
            onEditClick = {},
            onDeleteClick = {}
        )
    }
}