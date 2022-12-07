package com.puzzle.industries.budgetplan.viewModels.budget.expenses

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
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.services.CalendarService
import com.puzzle.industries.domain.services.CountryCurrencyService
import com.puzzle.industries.domain.services.DebtService
import com.puzzle.industries.domain.services.MonthTotalAmountCalculatorService
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.*
import java.util.*

class AddEditExpenseViewModel @AssistedInject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val expenseGroupId: UUID,
    private val debtService: DebtService,
    private val countryCurrencyService: CountryCurrencyService,
    private val prevExpense: Expense?,
    val calendarService: CalendarService
) : ViewModel(),
    CurrencySymbolObserverDelegate by CurrencySymbolObserverDelegateImpl(countryCurrencyService),
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

    val currencySymbol: StateFlow<String> = currencySymbolFlow

    private val _remainAmountResult: MutableStateFlow<DebtCheckResult?> by lazy {
        MutableStateFlow(
            value = null
        )
    }
    val remainingAmountResult: StateFlow<DebtCheckResult?> = _remainAmountResult

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
        initDebtAllowedFlow()
        initDebtFlows()
        initRemainingAmountFlow()
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
        val requiredInputsCondition = combine(
            titleStateFlowHandler.valueStateFlow,
            amountStateFlowHandler.valueStateFlow,
            descriptionStateFlowHandler.valueStateFlow,
            frequencyTypeStateFlowHandler.valueStateFlow,
            frequencyWhenStateFlowHandler.valueStateFlow
        ) { title, amount, desc, type, _when ->
            var valueChangedCondition = true

            if (isUpdatingConditionHandler.getValue()) {
                valueChangedCondition = title != prevExpense?.name
                        || amount != prevExpense.amount
                        || desc != prevExpense.description
                        || type != prevExpense.frequencyType
                        || _when != prevExpense.frequencyWhen
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

        requiredConditions.distinctUntilChanged().collectLatest { allInputsMeetCondition ->
            requiredInputsStateFlowHandler.onValueChange(allInputsMeetCondition)
        }
    }

    private fun initDebtAllowedFlow() = runCoroutine {
        debtService.getDebtAllowedState().collectLatest { allowDebt ->
            _allowDebt.value = allowDebt
        }
    }

    private fun initDebtFlows() = runCoroutine {
        combine(
            amountStateFlowHandler.valueStateFlow,
            frequencyWhenStateFlowHandler.valueStateFlow
        ) { _, _ -> expense }
            .distinctUntilChanged().collectLatest {
                if (isUpdatingConditionHandler.getValue()) {
                    _debtCheckResult.value =
                        debtService.willBeInDebtAfterModifyingExpense(expense = it)
                } else {
                    _debtCheckResult.value =
                        debtService.willBeInDebtAfterAddingExpense(expense = it)
                }
            }
    }

    private fun initRemainingAmountFlow() = runCoroutine {
        combine(
            amountStateFlowHandler.valueStateFlow,
            frequencyWhenStateFlowHandler.valueStateFlow
        ) { _, _ -> expense }
            .distinctUntilChanged().collectLatest {
                val result = debtService.getRemainingAmountAfterAllExpenses(
                    expense = it,
                    isAnExistingExpense = isUpdatingConditionHandler.getValue()
                )
                if(result.willBeInDebt){
                    _remainAmountResult.value = DebtCheckResult(
                        willBeInDebt = true,
                        amount = -result.amount,
                        forMonth = result.forMonth,
                        forYear = result.forYear
                    )
                }
                else _remainAmountResult.value = result
            }
    }
}