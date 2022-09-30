package com.puzzle.industries.budgetplan.viewModels.budget

import androidx.lifecycle.ViewModel
import androidx.lifecycle.ViewModelProvider
import com.puzzle.industries.budgetplan.factory.FrequencyDateFactory
import com.puzzle.industries.budgetplan.factory.viewModel.AddEditIncomeViewModelFactory
import com.puzzle.industries.domain.constants.FrequencyType
import com.puzzle.industries.domain.constants.WeekDays
import com.puzzle.industries.domain.models.income.Income
import dagger.assisted.Assisted
import dagger.assisted.AssistedInject
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow
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

    private val _title: MutableStateFlow<String> by lazy {
        MutableStateFlow(
            value = prevIncome?.title ?: ""
        )
    }
    val title: StateFlow<String> = _title.asStateFlow()
    val onTitleChange: (String) -> Unit = { it -> _title.value = it }

    private val _amount: MutableStateFlow<Double> by lazy {
        MutableStateFlow(
            value = prevIncome?.amount ?: 0.0
        )
    }
    val amount: StateFlow<Double> = _amount.asStateFlow()
    val onAmountChange: (Double) -> Unit = { it -> _amount.value = it }

    private val _description: MutableStateFlow<String> by lazy {
        MutableStateFlow(
            value = prevIncome?.description ?: ""
        )
    }
    val description: StateFlow<String> = _description.asStateFlow()
    val onDescriptionChange: (String) -> Unit = { it -> _description.value = it }

    private val _frequencyType: MutableStateFlow<FrequencyType> by lazy {
        MutableStateFlow(
            value = prevIncome?.frequencyType ?: FrequencyType.MONTHLY
        )
    }
    val frequencyType: StateFlow<FrequencyType> = _frequencyType.asStateFlow()
    val onFrequencyTypeChange: (FrequencyType) -> Unit = { it ->
        _frequencyType.value = it
        _frequencyWhen.value = when (it) {
            FrequencyType.ONCE_OFF -> FrequencyDateFactory.createCurrentDate().toString()
            FrequencyType.MONTHLY -> "1"
            FrequencyType.DAILY -> ""
            FrequencyType.WEEKLY -> WeekDays.MONDAY.name
            FrequencyType.YEARLY -> FrequencyDateFactory.createCurrentDate().toDayMonthString()
        }
    }

    private val _frequencyWhen: MutableStateFlow<String> by lazy {
        MutableStateFlow(
            value = prevIncome?.frequencyWhen ?: "1"
        )
    }
    val frequencyWhen: StateFlow<String> = _frequencyWhen.asStateFlow()
    val onFrequencyWhenChange: (String) -> Unit = { it -> _frequencyWhen.value = it }

    fun allRequiredInputsProvided(): Boolean =
        _title.value.isBlank().not() && _amount.value > 0

    val isUpdating: Boolean = prevIncome != null

    val income: Income
        get() = Income(
            id = prevIncome?.id ?: UUID.randomUUID(),
            frequencyType = frequencyType.value,
            frequencyWhen = frequencyWhen.value,
            amount = amount.value,
            title = title.value,
            description = description.value
        )

}