package com.puzzle.industries.budgetplan.screens.budget.expense

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.*
import androidx.compose.foundation.lazy.LazyRow
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.collectAsState
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.draw.rotate
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.LayoutDirection
import androidx.compose.ui.unit.dp
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.components.inputs.DescriptionInput
import com.puzzle.industries.budgetplan.components.inputs.TitleInput
import com.puzzle.industries.budgetplan.components.layout.OrientationAwareContentWrapper
import com.puzzle.industries.budgetplan.components.spacer.V_M_Space
import com.puzzle.industries.budgetplan.theme.colorPicker.ColorPickerSet
import com.puzzle.industries.budgetplan.theme.factory.ColorPickerColorsFactory
import com.puzzle.industries.budgetplan.theme.spacing
import com.puzzle.industries.budgetplan.util.layout.buildOrientationAwareActions
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.AddEditExpenseGroupViewModel
import com.puzzle.industries.budgetplan.viewModels.budget.expenses.ExpenseViewModel
import kotlinx.coroutines.flow.collectLatest

@Composable
fun AddEditExpenseGroupScreen(
    expenseViewModel: ExpenseViewModel,
    addEditExpenseGroupViewModel: AddEditExpenseGroupViewModel,
    isInTabletLandscape: Boolean,
    onNavigateBackToParent: () -> Unit
) {

    SetUpCrudResponseEventListener(
        expenseViewModel = expenseViewModel,
        dismiss = onNavigateBackToParent
    )

    OrientationAwareContentWrapper(
        title = stringResource(id = R.string.expense_category),
        subTitle = stringResource(
            id = if (addEditExpenseGroupViewModel.isUpdatingConditionHandler.getValue())
                R.string.update_expense_category_subtitle
            else R.string.add_expense_category_subtitle
        ),
        onDismiss = onNavigateBackToParent,
        isInTabletLandscape = isInTabletLandscape,
        actions = getOrientationActions(
            expenseViewModel = expenseViewModel,
            addEditExpenseGroupViewModel = addEditExpenseGroupViewModel,
            isInLandscape = isInTabletLandscape,
            onDismiss = onNavigateBackToParent
        )
    ) { paddingValues ->
        Column {
            Column(modifier = Modifier.padding(paddingValues = paddingValues)) {

                TitleTextField(addEditExpenseGroupViewModel = addEditExpenseGroupViewModel)
                V_M_Space()

                DescriptionTextField(addEditExpenseGroupViewModel = addEditExpenseGroupViewModel)

                V_M_Space()

                Text(
                    text = stringResource(id = R.string.note_pick_color),
                    style = MaterialTheme.typography.bodySmall
                )
            }

            ColorPickerField(
                addEditExpenseGroupViewModel = addEditExpenseGroupViewModel,
                horizontalPadding = paddingValues.calculateStartPadding(layoutDirection = LayoutDirection.Ltr)
            )
        }

    }
}

@Composable
private fun ColorPickerField(
    addEditExpenseGroupViewModel: AddEditExpenseGroupViewModel,
    horizontalPadding: Dp
) {
    val colorSets = ColorPickerColorsFactory.getColorPickerSet()
    val selectedColorId by addEditExpenseGroupViewModel.colorIdStateFlowHandler.valueStateFlow.collectAsState()

    LazyRow(
        modifier = Modifier.fillMaxWidth(),
        contentPadding = PaddingValues(
            vertical = MaterialTheme.spacing.medium,
            horizontal = horizontalPadding
        ),
        horizontalArrangement = Arrangement.spacedBy(space = MaterialTheme.spacing.medium)
    ) {
        items(colorSets) { colorSet ->
            ColorSetItem(
                colorSet = colorSet,
                isSelected = colorSet.name == selectedColorId,
                onClick = addEditExpenseGroupViewModel.colorIdStateFlowHandler.onValueChange
            )
        }

    }
}

@Composable
private fun ColorSetItem(colorSet: ColorPickerSet, isSelected: Boolean, onClick: (String) -> Unit) {
    val roundedShape = RoundedCornerShape(size = 16.dp)
    Box(
        modifier = Modifier
            .size(size = 32.dp)
            .clip(shape = roundedShape)
            .rotate(degrees = 45f)
            .border(
                width = if (isSelected) 3.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.tertiary else Color.Transparent,
                shape = roundedShape
            )
            .clickable {
                onClick(colorSet.name)
            }
    ) {
        Row(modifier = Modifier.fillMaxSize()) {
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f)
                    .background(color = colorSet.light)
            )
            Box(
                modifier = Modifier
                    .fillMaxHeight()
                    .weight(weight = 1f)
                    .background(color = colorSet.dark)
            )
        }
    }
}

@Composable
private fun SetUpCrudResponseEventListener(
    expenseViewModel: ExpenseViewModel,
    dismiss: () -> Unit
) {
    LaunchedEffect(key1 = true) {
        expenseViewModel.crudResponseEventListener.collectLatest {
            if (it.response) {
                dismiss()
            } else {
                //TODO: show error message
            }
        }
    }
}

@Composable
private fun getOrientationActions(
    expenseViewModel: ExpenseViewModel,
    addEditExpenseGroupViewModel: AddEditExpenseGroupViewModel,
    isInLandscape: Boolean,
    onDismiss: () -> Unit
): @Composable RowScope.() -> Unit {
    val isUpdating = addEditExpenseGroupViewModel.isUpdatingConditionHandler.getValue()
    val saveUpdateText: String = stringResource(
        id = if (isUpdating) R.string.update_expense_category else R.string.save_expense_category
    )
    val saveOrUpdateReason =
        stringResource(id = if (isUpdating) R.string.update else R.string.initial)

    val saveOrUpdateExpenseGroupClick: () -> Unit = {
        if (isUpdating) {
            expenseViewModel.updateExpenseGroup(
                reason = saveOrUpdateReason,
                addEditExpenseGroupViewModel.expenseGroup
            )
        } else {
            expenseViewModel.saveExpenseGroup(
                reason = saveOrUpdateReason,
                addEditExpenseGroupViewModel.expenseGroup
            )
        }
    }

    val allInputsEntered by addEditExpenseGroupViewModel.requiredInputsStateFlowHandler.valueStateFlow.collectAsState()

    return buildOrientationAwareActions(
        isInLandscape = isInLandscape,
        saveUpdateText = saveUpdateText,
        allInputsEntered = allInputsEntered,
        onSaveOrUpdateClickListener = saveOrUpdateExpenseGroupClick,
        onDismiss = onDismiss
    )
}

@Composable
private fun TitleTextField(addEditExpenseGroupViewModel: AddEditExpenseGroupViewModel) {
    val title by addEditExpenseGroupViewModel.titleStateFlowHandler.valueStateFlow.collectAsState()

    TitleInput(
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Next,
        title = title,
        onValueChange = addEditExpenseGroupViewModel.titleStateFlowHandler.onValueChange
    )
}

@Composable
private fun DescriptionTextField(addEditExpenseGroupViewModel: AddEditExpenseGroupViewModel) {
    val description by addEditExpenseGroupViewModel.descriptionStateFlowHandler.valueStateFlow.collectAsState()

    DescriptionInput(
        modifier = Modifier.fillMaxWidth(),
        imeAction = ImeAction.Done,
        description = description,
        onValueChange = addEditExpenseGroupViewModel.descriptionStateFlowHandler.onValueChange
    )
}
