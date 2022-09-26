package com.puzzle.industries.budgetplan.viewModels.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.viewModels.custom.CoroutineViewModel
import com.puzzle.industries.budgetplan.viewModels.custom.implementation.CoroutineViewModelImpl
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.CountryCurrencyPreferenceService
import com.puzzle.industries.domain.usescases.income.IncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*
import javax.inject.Inject

@HiltViewModel
class IncomeViewModel @Inject constructor(
    private val incomeUseCase: IncomeUseCase,
    private val countryCurrencyPreferenceService: CountryCurrencyPreferenceService,
) : ViewModel(), CoroutineViewModel by CoroutineViewModelImpl() {

    private val _incomes = MutableStateFlow<List<Income>>(value = emptyList())
    val incomes = _incomes.asStateFlow()

    private val _response = MutableLiveData<Response<Boolean>>()
    val response:LiveData<Response<Boolean>> = _response

    init {
        runCoroutine {
            val response: Response<Flow<List<Income>>> = incomeUseCase.read.read()
            response.response.distinctUntilChanged().collect { incomeList ->
                if (incomeList.isNotEmpty()) _incomes.value = incomeList
            }
        }
    }

    fun reactToResponseOnce(action: () -> Unit){
        _response.value = null
        action()

    }

    fun getTotalIncome(): Double = incomes.value.sumOf { income -> income.amount }

    fun getTotalIncomeWithCurrency(): String =
        "${countryCurrencyPreferenceService.getCurrencySymbol()}${getTotalIncome()}"

    fun getIncomeById(id: String?): Income? {
        return incomes.value.find { income -> income.id == UUID.fromString(id) }
    }

    fun saveIncome(income: Income, reason: String) {
        runCoroutine {
            _response.value = incomeUseCase.create.insert(reason = reason, entity = arrayOf(income))
        }
    }

    fun updateIncome(income: Income, reason: String) {
        runCoroutine {
            _response.value = incomeUseCase.update.update(reason = reason, entity = arrayOf(income))
        }
    }
}