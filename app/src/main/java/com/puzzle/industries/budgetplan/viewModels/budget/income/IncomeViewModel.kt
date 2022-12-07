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
import com.puzzle.industries.domain.models.DebtCheckResult
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.CountryCurrencyService
import com.puzzle.industries.domain.services.DebtService
import com.puzzle.industries.domain.services.MonthTotalAmountCalculatorService
import com.puzzle.industries.domain.usescases.income.IncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val incomeUseCase: IncomeUseCase,
    private val countryCurrencyService: CountryCurrencyService,
    private val monthTotalCalculator: MonthTotalAmountCalculatorService,
    private val debtService: DebtService
) : ViewModel(),
    CurrencySymbolObserverDelegate by CurrencySymbolObserverDelegateImpl(countryCurrencyService),
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

    private val _selectedYearForIncome: MutableStateFlow<Int?> = MutableStateFlow(value = null)
    private val _selectedMonthForIncome: MutableStateFlow<Months?> = MutableStateFlow(value = null)
    fun onSelectedMonthYearForIncomeChange(month: Months? = null, year: Int? = null) {
        _selectedMonthForIncome.value = month
        _selectedYearForIncome.value = year
    }

    private val _totalIncomeForSelectedMonth = MutableStateFlow(value = 0.0)
    val totalIncomeForSelectedMonth: StateFlow<Double> = _totalIncomeForSelectedMonth

    private val _debtAllowed = MutableStateFlow(value = false)

    private val _debtWarning by lazy {
        MutableSharedFlow<DebtCheckResult>()
    }
    val debtWarning: SharedFlow<DebtCheckResult> by lazy {
        _debtWarning
    }

    init {
        initIncomes()
        initTotalIncome()
        initTotalIncomeForMonth()
        initTotalIncomeForSelectedMonthYear()
        initDebtAllowedFlow()
    }

    private fun initIncomes() = runCoroutine {
        val response: Response<Flow<List<Income>>> = incomeUseCase.read.readAll()
        response.response.distinctUntilChanged().collectLatest { incomeList ->
            items.value = incomeList
        }
    }

    private fun initTotalIncome() = runCoroutine {
        incomes.collectLatest { incomes ->
            _totalIncome.value = incomes.sumOf { income -> income.amount }
        }
    }

    private fun initTotalIncomeForMonth() = runCoroutine {
        incomes.collectLatest { incomes ->
            val total = monthTotalCalculator.calculateTotalIncomesForMonth(incomes = incomes)
            _totalIncomeForCurrentMonth.value = total

        }
    }

    private fun initTotalIncomeForSelectedMonthYear() = runCoroutine {
        combine(
            incomes,
            _selectedMonthForIncome,
            _selectedYearForIncome
        ) { incomes, selectedMonth, selectedYear ->
            monthTotalCalculator.calculateTotalIncomesForMonth(
                month = selectedMonth,
                year = selectedYear,
                incomes = incomes
            )
        }.distinctUntilChanged().collectLatest { totalIncomeForSelectedMonth ->
            _totalIncomeForSelectedMonth.value = totalIncomeForSelectedMonth
        }
    }

    private fun initDebtAllowedFlow() = runCoroutine {
        debtService.getDebtAllowedState().collectLatest {
            _debtAllowed.value = it
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
        if (!_debtAllowed.value){
            val debtResult = willBeInDebtAfterRemovingIncomes(incomes = incomes)
            if (debtResult.willBeInDebt){
                _debtWarning.emit(value = debtResult)
            }
            else{
                initiateIncomeDelete(reason = reason, incomes = incomes)
            }
        }
        else {
            initiateIncomeDelete(reason = reason, incomes = incomes)
        }
    }

    private fun willBeInDebtAfterRemovingIncomes(vararg incomes: Income):DebtCheckResult {
        return debtService.willBeInDebtAfterRemovingIncomes(incomes = incomes)
    }

    fun forceDeleteIncome(reason: String, vararg incomes: Income) = runCoroutine {
        initiateIncomeDelete(reason = reason, incomes = incomes)
    }

    private fun initiateIncomeDelete(reason: String, vararg incomes: Income) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = incomeUseCase.delete.delete(
                reason = reason,
                entity = incomes
            )
        )
    }
}