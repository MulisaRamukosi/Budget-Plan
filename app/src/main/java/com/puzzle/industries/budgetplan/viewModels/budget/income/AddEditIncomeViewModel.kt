package com.puzzle.industries.budgetplan.viewModels.budget.income

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.AddEditItemStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CurrencySymbolObserverDelegate
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.AddEditItemStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CurrencySymbolObserverDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.SavedStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.util.configs.FrequencyConfig
import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.models.DebtCheckResult
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.services.DebtService
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import java.util.*

class AddEditIncomeViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val prevIncome: Income?,
    private val countryCurrencyDataStore: CountryCurrencyDataStore,
    private val debtService: DebtService,
    val calendarService: CalendarService
) : ViewModel(),
    CurrencySymbolObserverDelegate by CurrencySymbolObserverDelegateImpl(countryCurrencyDataStore),
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

    val currencySymbol: StateFlow<String> = currencySymbolFlow

    private val _allowDebt: MutableStateFlow<Boolean> = MutableStateFlow(value = false)
    val allowDebt: StateFlow<Boolean> = _allowDebt

    private val _debtCheckResult: MutableStateFlow<DebtCheckResult> =
        MutableStateFlow(
            value = DebtCheckResult(
                willBeInDebt = false,
                amount = 0.0,
                forMonth = Months.JANUARY,
                forYear = 2022
            )
        )
    val debtCheckResult: StateFlow<DebtCheckResult> = _debtCheckResult

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
        initDebtAllowedFlow()
        initDebtFlows()
    }

    private fun cacheIncomeId() {
        prevIncome?.let {
            setStateFlowValue(key = Income::id.name, value = it.id)
        }
    }

    private fun initAllInputsValidFlow() {
        runCoroutine {
            val requiredInputsCondition: Flow<Boolean> = combine(
                titleStateFlowHandler.valueStateFlow,
                amountStateFlowHandler.valueStateFlow,
                descriptionStateFlowHandler.valueStateFlow,
                frequencyTypeStateFlowHandler.valueStateFlow,
                frequencyWhenStateFlowHandler.valueStateFlow
            ) { title, amount, desc, type, _when ->
                var valueChangedCondition = true

                if (isUpdatingConditionHandler.getValue()) {
                    valueChangedCondition = title != prevIncome?.title
                            || amount != prevIncome.amount
                            || desc != prevIncome.description
                            || type != prevIncome.frequencyType
                            || _when != prevIncome.frequencyWhen
                }

                valueChangedCondition && title.isBlank().not() && amount > 0
            }

            val requiredConditions = combine(
                requiredInputsCondition,
                _allowDebt,
                _debtCheckResult
            ) { inputsCondition, allowDebt, debtCheckResult ->
                if (!allowDebt) return@combine inputsCondition && !debtCheckResult.willBeInDebt
                return@combine inputsCondition
            }

            requiredConditions.distinctUntilChanged().collect { allInputsMeetCondition ->
                requiredInputsStateFlowHandler.onValueChange(allInputsMeetCondition)
            }
        }
    }

    private fun initDebtAllowedFlow() = runCoroutine {
        debtService.getDebtAllowedState().collect { allowDebt ->
            _allowDebt.value = allowDebt
        }
    }

    private fun initDebtFlows() = runCoroutine {
        combine(
            amountStateFlowHandler.valueStateFlow,
            frequencyTypeStateFlowHandler.valueStateFlow
        ) { _, _ -> income }
            .distinctUntilChanged().collect {
                if (isUpdatingConditionHandler.getValue()) {
                    _debtCheckResult.value =
                        debtService.willBeInDebtAfterModifyingIncome(income = it)
                }
            }
    }

}