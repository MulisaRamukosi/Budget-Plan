package com.puzzle.industries.budgetplan.viewModels.budget.expenses

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.SavedStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.theme.factory.ColorPickerColorsFactory
import com.puzzle.industries.budgetplan.util.configs.EditConfig
import com.puzzle.industries.budgetplan.util.configs.ValidationConfig
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

class AddEditExpenseGroupViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val prevExpenseGroup: ExpenseGroup?
) : ViewModel(),
    SavedStateHandlerDelegate by SavedStateHandlerDelegateImpl(savedStateHandle),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    val titleStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = ExpenseGroup::name.name,
            initialValue = ""
        )
    }

    val descriptionStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = ExpenseGroup::description.name,
            initialValue = ""
        )
    }

    val colorIdStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = ExpenseGroup::colorId.name,
            initialValue = ColorPickerColorsFactory.getColorPickerSet().first().name
        )
    }

    val requiredInputsStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = ValidationConfig.VALIDATION_KEY,
            initialValue = titleStateFlowHandler.getValue().isBlank().not()
        )
    }

    val isUpdatingConditionHandler by lazy {
        registerKeyStateFlowHandler(
            key = EditConfig.UPDATE_KEY,
            initialValue = prevExpenseGroup != null
        )
    }

    val expenseGroup: ExpenseGroup
        get() = ExpenseGroup(
            id = getStateFlow(key = ExpenseGroup::id.name, initialValue = UUID.randomUUID()).value,
            name = titleStateFlowHandler.getValue(),
            description = descriptionStateFlowHandler.getValue(),
            colorId = colorIdStateFlowHandler.getValue()
        )

    init {
        cacheExpenseGroup()
        initAllInputsValidFlow()
    }

    private fun cacheExpenseGroup() {
        prevExpenseGroup?.let {
            setStateFlowValue(key = ExpenseGroup::id.name, value = it.id)
            setStateFlowValue(key = ExpenseGroup::name.name, value = it.name)
            setStateFlowValue(key = ExpenseGroup::description.name, value = it.description)
            setStateFlowValue(key = ExpenseGroup::colorId.name, value = it.colorId)
        }
    }

    private fun initAllInputsValidFlow() = runCoroutine {
        val requiredInputsCheckFlow: Flow<Boolean> = combine(
            titleStateFlowHandler.valueStateFlow,
            descriptionStateFlowHandler.valueStateFlow,
            colorIdStateFlowHandler.valueStateFlow
        ) { title, description, colorId ->
            var valueChangedCondition = true

            if (isUpdatingConditionHandler.getValue()) {
                valueChangedCondition =
                    title != prevExpenseGroup?.name
                            || description != prevExpenseGroup.description
                            || colorId != prevExpenseGroup.colorId
            }

            valueChangedCondition && title.isNotBlank()
        }

        requiredInputsCheckFlow.distinctUntilChanged()
            .collect { allInputsMeetCondition ->
                requiredInputsStateFlowHandler.onValueChange(allInputsMeetCondition)
            }
    }

}