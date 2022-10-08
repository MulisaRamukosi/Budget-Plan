package com.puzzle.industries.budgetplan.viewModels.budget.income

import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CrudViewModelHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CrudViewModelHandlerDelegateImpl
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
    currencyPreferenceService: CountryCurrencyPreferenceService
) : ViewModel(),
    CrudViewModelHandlerDelegate<Boolean, Income> by CrudViewModelHandlerDelegateImpl(),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    val incomes = items.asStateFlow()
    val crudResponseEventListener: SharedFlow<Response<Boolean>> = crudResponseEventEmitter

    val deleteIncomeEventListener: SharedFlow<Income> = deleteValueEventEmitter
    val onDeleteIncome: (Income) -> Unit = onDeleteValue

    val updateIncomeEventListener: SharedFlow<Unit> = updateValueEventEmitter
    val emitUpdateIncomeEvent: () -> Unit = onUpdateValue

    val currencySymbol = currencyPreferenceService.getCurrencySymbol()

    private val _totalIncome = MutableStateFlow(value = 0.0)
    val totalIncome = _totalIncome

    init {
        initIncomes()
        initTotalIncome()
    }

    private fun initIncomes() = runCoroutine {
        val response: Response<Flow<List<Income>>> = incomeUseCase.read.read()
        response.response.distinctUntilChanged().collect { incomeList ->
            items.value = incomeList
        }
    }

    private fun initTotalIncome() = runCoroutine {
        incomes.collect { incomes ->
            _totalIncome.value = incomes.sumOf { income -> income.amount }
        }
    }

    fun getIncomeById(id: String?): Income? = incomes.value.find { income ->
        income.id == UUID.fromString(id)
    }

    fun saveIncome(income: Income, reason: String) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = incomeUseCase.create.insert(
                reason = reason,
                entity = arrayOf(income)
            )
        )
    }

    fun updateIncome(income: Income, reason: String) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = incomeUseCase.update.update(
                reason = reason,
                entity = arrayOf(income)
            )
        )
    }

    fun deleteIncome(income: Income, reason: String) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = incomeUseCase.delete.delete(
                reason = reason,
                entity = arrayOf(income)
            )
        )
    }
}