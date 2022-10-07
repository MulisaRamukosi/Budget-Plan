package com.puzzle.industries.budgetplan.viewModels.budget.income

import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.CountryCurrencyPreferenceService
import com.puzzle.industries.domain.usescases.income.IncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val incomeUseCase: IncomeUseCase,
    countryCurrencyPreferenceService: CountryCurrencyPreferenceService,
) : ViewModel(), CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    private val _incomes = MutableStateFlow<List<Income>>(value = emptyList())
    val incomes = _incomes.asStateFlow()

    private val _crudResponseEventEmitter = MutableSharedFlow<Response<Boolean>>()
    val crudResponseEventListener: SharedFlow<Response<Boolean>> = _crudResponseEventEmitter

    private val _currencySymbol =
        MutableStateFlow(value = countryCurrencyPreferenceService.getCurrencySymbol())
    val currencySymbol = _currencySymbol.asStateFlow()

    private val _totalIncomeWithCurrency = MutableStateFlow(value = "")
    val totalIncomeWithCurrency = _totalIncomeWithCurrency.asStateFlow()

    private val _deleteIncomeEventEmitter = MutableSharedFlow<Income>()
    val deleteIncomeEventListener: SharedFlow<Income> = _deleteIncomeEventEmitter
    val onDeleteIncome: (Income) -> Unit = {
        runCoroutine {
            _deleteIncomeEventEmitter.emit(value = it)
        }
    }

    private val _updateIncomeEventEmitter = MutableSharedFlow<Unit>()
    val updateIncomeEventListener: SharedFlow<Unit> = _updateIncomeEventEmitter
    val emitUpdateIncomeEvent: () -> Unit = {
        runCoroutine {
            _updateIncomeEventEmitter.emit(value = Unit)
        }
    }

    init {
        initIncomeFlow()
        initTotalIncomeWithCurrencyFlow()
    }

    private fun initIncomeFlow() {
        runCoroutine {
            val response: Response<Flow<List<Income>>> = incomeUseCase.read.read()
            response.response.distinctUntilChanged().collect { incomeList ->
                if (incomeList.isNotEmpty()) _incomes.value = incomeList
            }
        }
    }

    private fun initTotalIncomeWithCurrencyFlow() {
        runCoroutine {
            val totalIncomeWithCurrencyFlow: Flow<String> =
                _incomes.combine(_currencySymbol) { incomes, currencySymbol ->
                    val totalIncome = incomes.sumOf { income -> income.amount }
                    "$currencySymbol${totalIncome.toBigDecimal().toPlainString()}"
                }
            totalIncomeWithCurrencyFlow.distinctUntilChanged().collect { totalIncome ->
                _totalIncomeWithCurrency.value = totalIncome
            }
        }
    }

    fun getIncomeById(id: String?): Income? {
        return incomes.value.find { income -> income.id == UUID.fromString(id) }
    }

    fun saveIncome(income: Income, reason: String) {
        runCoroutine {
            _crudResponseEventEmitter.emit(
                value = incomeUseCase.create.insert(
                    reason = reason,
                    entity = arrayOf(income)
                )
            )
        }
    }

    fun updateIncome(income: Income, reason: String) {
        runCoroutine {
            _crudResponseEventEmitter.emit(
                value = incomeUseCase.update.update(
                    reason = reason,
                    entity = arrayOf(income)
                )
            )
        }
    }

    fun deleteIncome(income: Income, reason: String) {
        runCoroutine {
            _crudResponseEventEmitter.emit(
                value = incomeUseCase.delete.delete(
                    reason = reason,
                    entity = arrayOf(income)
                )
            )
        }
    }
}