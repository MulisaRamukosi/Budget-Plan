package com.puzzle.industries.budgetplan.viewModels.budget

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puzzle.industries.budgetplan.factory.viewModel.AddEditIncomeViewModelFactory
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.models.income.Income
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import java.util.*

class AddEditIncomeViewModel @AssistedInject constructor(
    @Assisted private val prevIncome: Income?
) : ViewModel() {

    companion object {
        fun provideFactory(
            assistedFactory: AddEditIncomeViewModelFactory,
            prevIncome: Income?
        ): ViewModelProvider.Factory = object : ViewModelProvider.Factory {
            override fun <T : ViewModel> create(modelClass: Class<T>): T {
                return assistedFactory.create(prevIncome) as T
            }
        }
    }

    private val _title: MutableLiveData<String> by lazy { MutableLiveData(prevIncome?.title) }
    val title: LiveData<String> = _title
    val onTitleChange: (String) -> Unit = { it -> _title.value = it }

    private val _amount: MutableLiveData<Double> by lazy { MutableLiveData(prevIncome?.amount) }
    val amount: LiveData<Double> = _amount
    val onAmountChange: (Double) -> Unit = { it -> _amount.value = it }

    private val _description: MutableLiveData<String> by lazy { MutableLiveData(prevIncome?.description) }
    val description: LiveData<String> = _description
    val onDescriptionChange: (String) -> Unit = { it -> _description.value = it }

    private val _frequencyType: MutableLiveData<FrequencyType> by lazy { MutableLiveData(prevIncome?.frequencyType) }
    val frequencyType: LiveData<FrequencyType> = _frequencyType
    val onFrequencyTypeChange: (FrequencyType) -> Unit = { it -> _frequencyType.value = it }

    private val _frequencyWhen: MutableLiveData<String> by lazy { MutableLiveData(prevIncome?.frequencyWhen) }
    val frequencyWhen: LiveData<String> = _frequencyWhen
    val onFrequencyWhenChange: (String) -> Unit = {it -> _frequencyWhen.value = it }

    fun allRequiredInputsProvided(): Boolean =
        _title.value.isNullOrBlank().not() && (_amount.value ?: 0.0) > 0

    val isUpdating: Boolean = prevIncome != null

    val income: Income
        get() = Income(
            id = prevIncome?.id ?: UUID.randomUUID(),
            frequencyType = frequencyType.value ?: FrequencyType.MONTHLY,
            frequencyWhen = frequencyWhen.value ?: "1",
            amount = amount.value ?: 0.0,
            title = title.value ?: "",
            description = description.value ?: ""
        )

}