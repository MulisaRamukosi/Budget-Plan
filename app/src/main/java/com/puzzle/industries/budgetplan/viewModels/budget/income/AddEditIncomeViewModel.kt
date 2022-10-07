package com.puzzle.industries.budgetplan.viewModels.budget.income

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.SavedStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.factory.FrequencyDateFactory
import com.puzzle.industries.budgetplan.util.configs.EditConfig
import com.puzzle.industries.budgetplan.util.configs.FrequencyConfig
import com.puzzle.industries.budgetplan.util.configs.ValidationConfig
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.income.Income
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*

class AddEditIncomeViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val prevIncome: Income?
) : ViewModel(),
    SavedStateHandlerDelegate by SavedStateHandlerDelegateImpl(savedStateHandle),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    val titleStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::title.name,
            initialValue = ""
        )
    }

    val amountStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::amount.name,
            initialValue = 0.0
        )
    }

    val descriptionStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::description.name,
            initialValue = ""
        )
    }

    val frequencyTypeStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::frequencyType.name,
            initialValue = FrequencyConfig.DEFAULT_TYPE
        )
    }

    val frequencyWhenStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = Income::frequencyWhen.name,
            initialValue = FrequencyConfig.DEFAULT_WHEN
        )
    }

    val requiredInputsStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = ValidationConfig.VALIDATION_KEY,
            initialValue = titleStateFlowHandler.getValue().isBlank()
                .not() && amountStateFlowHandler.getValue() > 0
        )
    }

    val isUpdatingConditionHandler by lazy {
        registerKeyStateFlowHandler(
            key = EditConfig.UPDATE_KEY,
            initialValue = prevIncome != null
        )
    }

    private val _crudActionReason: MutableStateFlow<String> by lazy { MutableStateFlow(value = "") }
    val onCrudActionReasonChange: (String) -> Unit = { newCrudActionReason ->
        _crudActionReason.value = newCrudActionReason
    }
    val crudActionReason: String
        get() = _crudActionReason.value

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
        cacheIncome()
        initFrequencyWhenFlow()
        initAllInputsValidFlow()
    }

    private fun cacheIncome() {
        prevIncome?.let {
            setStateFlowValue(key = Income::id.name, value = it.id)
            setStateFlowValue(key = Income::title.name, value = it.title)
            setStateFlowValue(key = Income::amount.name, value = it.amount)
            setStateFlowValue(key = Income::description.name, value = it.description)
            setStateFlowValue(key = Income::frequencyType.name, value = it.frequencyType)
            setStateFlowValue(key = Income::frequencyWhen.name, value = it.frequencyWhen)
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

    private fun initFrequencyWhenFlow() {
        runCoroutine {
            frequencyTypeStateFlowHandler.valueStateFlow.collect { type ->
                frequencyWhenStateFlowHandler.onValueChange(
                    when (type) {
                        FrequencyType.ONCE_OFF -> FrequencyDateFactory.createCurrentDate()
                            .toString()
                        FrequencyType.MONTHLY -> FrequencyConfig.DEFAULT_WHEN
                        FrequencyType.DAILY -> ""
                        FrequencyType.WEEKLY -> WeekDays.MONDAY.name
                        FrequencyType.YEARLY -> FrequencyDateFactory.createCurrentDate()
                            .toDayMonthString()
                    }
                )
            }
        }
    }
}