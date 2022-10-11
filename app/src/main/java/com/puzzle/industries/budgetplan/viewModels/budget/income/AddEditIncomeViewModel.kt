package com.puzzle.industries.budgetplan.viewModels.budget.income

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.AddEditItemStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.AddEditItemStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.SavedStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.factory.FrequencyDateFactory
import com.puzzle.industries.budgetplan.util.configs.FrequencyConfig
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.income.Income
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

class AddEditIncomeViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val prevIncome: Income?
) : ViewModel(),
    SavedStateHandlerDelegate by SavedStateHandlerDelegateImpl(savedStateHandle),
    AddEditItemStateHandlerDelegate by AddEditItemStateHandlerDelegateImpl(savedStateHandle),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    val titleStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::title.name,
            initialValue = prevIncome?.title ?: ""
        )
    }

    val amountStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::amount.name,
            initialValue = prevIncome?.amount ?: 0.0
        )
    }

    val descriptionStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::description.name,
            initialValue = prevIncome?.description ?: ""
        )
    }

    val frequencyTypeStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::frequencyType.name,
            initialValue = prevIncome?.frequencyType ?: FrequencyConfig.DEFAULT_TYPE
        )
    }

    val frequencyWhenStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::frequencyWhen.name,
            initialValue = prevIncome?.frequencyWhen ?: FrequencyConfig.DEFAULT_WHEN
        )
    }

    val requiredInputsStateFlowHandler by lazy {
        getRequiredInputsStateHandler(
            initialValue = titleStateFlowHandler.getValue().isBlank()
                .not() && amountStateFlowHandler.getValue() > 0
        )
    }

    val isUpdatingConditionHandler by lazy {
        getIsUpdatingConditionStateHandler(initialValue = prevIncome != null)
    }

    val crudActionReasonHandler by lazy {
        getActionReasonStateHandler()
    }

    val income: Income
        get() = Income(
            id = getStateFlow(key = Income::id.name, initialValue = UUID.randomUUID()).value,
            frequencyType = frequencyTypeStateFlowHandler.getValue(),
            frequencyWhen = frequencyWhenStateFlowHandler.getValue(),
            amount = amountStateFlowHandler.getValue(),
            title = titleStateFlowHandler.getValue(),
            description = descriptionStateFlowHandler.getValue()
        )

    init {
        cacheIncomeId()
        initAllInputsValidFlow()
    }

    private fun cacheIncomeId() {
        prevIncome?.let {
            setStateFlowValue(key = Income::id.name, value = it.id)
        }
    }

    private fun initAllInputsValidFlow() {
        runCoroutine {
            val requiredInputsCheckFlow: Flow<Boolean> =
                titleStateFlowHandler.valueStateFlow.combine(amountStateFlowHandler.valueStateFlow) { title, amount ->
                    title.isBlank().not() && amount > 0
                }
            requiredInputsCheckFlow.distinctUntilChanged().collect { allInputsProvided ->
                requiredInputsStateFlowHandler.onValueChange(allInputsProvided)
            }
        }
    }

}