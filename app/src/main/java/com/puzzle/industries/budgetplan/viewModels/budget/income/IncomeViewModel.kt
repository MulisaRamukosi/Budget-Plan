package com.puzzle.industries.budgetplan.viewModels.budget.income

import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CrudViewModelHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CurrencySymbolObserverDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CrudViewModelHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CurrencySymbolObserverDelegateImpl
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.constants.Months
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.MonthTotalAmountCalculatorService
import com.puzzle.industries.domain.usescases.income.IncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val incomeUseCase: IncomeUseCase,
    private val currencyPreferenceService: CountryCurrencyDataStore,
    private val monthTotalCalculator: MonthTotalAmountCalculatorService
) : ViewModel(),
    CurrencySymbolObserverDelegate by CurrencySymbolObserverDelegateImpl(currencyPreferenceService),
    CrudViewModelHandlerDelegate<Boolean, Income> by CrudViewModelHandlerDelegateImpl(),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    val incomes = items.asStateFlow()
    val crudResponseEventListener: SharedFlow<Response<Boolean>> = crudResponseEventEmitter

    val deleteIncomeEventListener: SharedFlow<Income> = deleteValueEventEmitter
    val onDeleteIncome: (Income) -> Unit = onDeleteValue

    val updateIncomeEventListener: SharedFlow<Unit> = updateValueEventEmitter
    val emitUpdateIncomeEvent: () -> Unit = onUpdateValue

    val currencySymbol: StateFlow<String> = currencySymbolFlow

    private val _totalIncome = MutableStateFlow(value = 0.0)
    val totalIncome: StateFlow<Double> = _totalIncome

    private val _totalIncomeForCurrentMonth = MutableStateFlow(value = 0.0)
    val totalIncomeForCurrentMonth: StateFlow<Double> = _totalIncomeForCurrentMonth

    private val _totalIncomeForMonth = MutableStateFlow(value = 0.0)
    val totalIncomeForMonth: StateFlow<Double> = _totalIncomeForMonth

    init {
        initIncomes()
        initTotalIncome()
        initTotalIncomeForMonth()
    }

    private fun initIncomes() = runCoroutine {
        val response: Response<Flow<List<Income>>> = incomeUseCase.read.readAll()
        response.response.distinctUntilChanged().collect { incomeList ->
            items.value = incomeList
        }
    }

    private fun initTotalIncome() = runCoroutine {
        incomes.collect { incomes ->
            _totalIncome.value = incomes.sumOf { income -> income.amount }
        }
    }

    private fun initTotalIncomeForMonth() = runCoroutine {
        incomes.collect { incomes ->
            val total = monthTotalCalculator.calculateTotalIncomesForMonth(incomes = incomes)
            _totalIncomeForCurrentMonth.value = total
            _totalIncomeForMonth.value = total

        }
    }

    fun getTotalIncomesForMonth(month: Int) = runCoroutine {
        incomes.collectLatest { incomes ->
            _totalIncomeForMonth.value = monthTotalCalculator.calculateTotalIncomesForMonth(
                month = Months.values()[month],
                incomes = incomes
            )
        }
    }

    fun getIncomeById(id: UUID): Income? = incomes.value.find { income ->
        income.id == id
    }

    fun saveIncome(reason: String, vararg incomes: Income) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = incomeUseCase.create.insert(
                reason = reason,
                entity = incomes
            )
        )
    }

    fun updateIncome(reason: String, vararg incomes: Income) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = incomeUseCase.update.update(
                reason = reason,
                entity = incomes
            )
        )
    }

    fun deleteIncome(reason: String, vararg incomes: Income) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = incomeUseCase.delete.delete(
                reason = reason,
                entity = incomes
            )
        )
    }
}