package com.puzzle.industries.budgetplan.viewModels.budget.expenses

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.AddEditItemStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.AddEditItemStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.SavedStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.util.configs.FrequencyConfig
import com.puzzle.industries.domain.models.expense.Expense
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

class AddEditExpenseViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val expenseGroupId: UUID,
    private val prevExpense: Expense?
) : ViewModel(),
    SavedStateHandlerDelegate by SavedStateHandlerDelegateImpl(savedStateHandle),
    AddEditItemStateHandlerDelegate by AddEditItemStateHandlerDelegateImpl(savedStateHandle),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    val titleStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Expense::name.name,
            initialValue = ""
        )
    }

    val descriptionStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Expense::description.name,
            initialValue = ""
        )
    }

    val amountStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Expense::amount.name,
            initialValue = 0.0
        )
    }

    val frequencyTypeStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Expense::frequencyType.name,
            initialValue = FrequencyConfig.DEFAULT_TYPE
        )
    }

    val frequencyWhenStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Expense::frequencyWhen.name,
            initialValue = FrequencyConfig.DEFAULT_WHEN
        )
    }

    val requiredInputsStateFlowHandler by lazy {
        getRequiredInputsStateHandler(
            initialValue = titleStateFlowHandler.getValue().isBlank()
                .not() && amountStateFlowHandler.getValue() > 0
        )
    }

    val isUpdatingConditionHandler by lazy {
        getIsUpdatingConditionStateHandler(initialValue = prevExpense != null)
    }

    val crudActionReasonHandler by lazy {
        getActionReasonStateHandler()
    }

    val expense: Expense
        get() = Expense(
            id = getStateFlow(key = Expense::id.name, initialValue = UUID.randomUUID()).value,
            expenseGroupId = getStateFlow(
                key = Expense::expenseGroupId.name,
                initialValue = expenseGroupId
            ).value,
            name = titleStateFlowHandler.getValue(),
            amount = amountStateFlowHandler.getValue(),
            description = descriptionStateFlowHandler.getValue(),
            frequencyType = frequencyTypeStateFlowHandler.getValue(),
            frequencyWhen = frequencyWhenStateFlowHandler.getValue()
        )

    init {
        cacheExpense()
        initAllInputsValidFlow()
    }

    private fun cacheExpense() {
        prevExpense?.let {
            setStateFlowValue(key = Expense::id.name, value = it.id)
            setStateFlowValue(key = Expense::expenseGroupId.name, value = it.expenseGroupId)
            setStateFlowValue(key = Expense::name.name, value = it.name)
            setStateFlowValue(key = Expense::amount.name, value = it.amount)
            setStateFlowValue(key = Expense::description.name, value = it.description)
            setStateFlowValue(key = Expense::frequencyType.name, value = it.frequencyType)
            setStateFlowValue(key = Expense::frequencyWhen.name, value = it.frequencyWhen)
        }
    }

    private fun initAllInputsValidFlow() = runCoroutine {
        val allConditions = combine(
            titleStateFlowHandler.valueStateFlow,
            amountStateFlowHandler.valueStateFlow,
            descriptionStateFlowHandler.valueStateFlow,
            frequencyTypeStateFlowHandler.valueStateFlow,
            frequencyWhenStateFlowHandler.valueStateFlow
        ) { title, amount, desc, type, _when ->
            var valueChangedCondition = true

            if(isUpdatingConditionHandler.getValue()){
                valueChangedCondition = title != prevExpense?.name
                        || amount != prevExpense.amount
                        || desc != prevExpense.description
                        || type != prevExpense.frequencyType
                        || _when != prevExpense.frequencyWhen
            }

            valueChangedCondition && title.isBlank().not() && amount > 0
        }

        allConditions.distinctUntilChanged().collect { allInputsMeetCondition ->
            requiredInputsStateFlowHandler.onValueChange(allInputsMeetCondition)
        }
    }
}