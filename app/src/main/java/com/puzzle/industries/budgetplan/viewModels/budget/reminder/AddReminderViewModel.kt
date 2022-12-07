package com.puzzle.industries.budgetplan.viewModels.budget.reminder

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CurrencySymbolObserverDelegate
import com.puzzle.industries.budgetplan.delegates.SavedStateHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CurrencySymbolObserverDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.SavedStateHandlerDelegateImpl
import com.puzzle.industries.budgetplan.util.configs.ValidationConfig
import com.puzzle.industries.data.util.config.ReminderConfig
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.services.CountryCurrencyService
import com.puzzle.industries.domain.usescases.reminder.ReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.coroutines.flow.distinctUntilChanged
import java.util.*
import javax.inject.Inject

@HiltViewModel
class AddReminderViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val reminderUseCase: ReminderUseCase,
    private val countryCurrencyService: CountryCurrencyService
) : ViewModel(),
    CurrencySymbolObserverDelegate by CurrencySymbolObserverDelegateImpl(countryCurrencyService),
    SavedStateHandlerDelegate by SavedStateHandlerDelegateImpl(savedStateHandle),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    private val selectedExpensesIdsHandler by lazy {
        registerKeyStateFlowHandler<List<UUID>>(
            key = ReminderConfig.KEY_SELECTED_IDS,
            initialValue = arrayListOf()
        )
    }

    val requiredInputsStateFlowHandler by lazy {
        registerKeyStateFlowHandler(
            key = ValidationConfig.VALIDATION_KEY,
            initialValue = false
        )
    }

    val currencySymbol: StateFlow<String> = currencySymbolFlow

    private val _expenses = MutableStateFlow<List<Expense>>(value = emptyList())
    val expenses: StateFlow<List<Expense>> = _expenses

    init {
        initRemindersWithNoAlarms()
        initInputsValidFlow()
    }

    private fun initInputsValidFlow() = runCoroutine {
        selectedExpensesIdsHandler.valueStateFlow.collectLatest { selectedExpenses ->
            requiredInputsStateFlowHandler.onValueChange(selectedExpenses.isNotEmpty())
        }
    }

    private fun initRemindersWithNoAlarms() = runCoroutine {
        reminderUseCase.readExpensesWithNoReminders.readAll().response.distinctUntilChanged()
            .collectLatest { expenseList ->
                _expenses.value = expenseList.filter { expense ->
                    ReminderConfig.SUPPORTED_FREQUENCY_TYPE_FOR_ALARM.contains(
                        expense.frequencyType
                    )
                }
            }
    }

    fun isSelected(id: UUID): Boolean = selectedExpensesIdsHandler.getValue().contains(id)

    fun onSelectExpense(id: UUID, selected: Boolean) {
        val selectedIds = selectedExpensesIdsHandler.getValue().toMutableList()

        if (selected) {
            selectedIds.add(id)
        } else {
            selectedIds.remove(id)
        }

        selectedExpensesIdsHandler.onValueChange(selectedIds.toList())
    }

    fun getSelectedExpenses(): List<Expense> {
        return selectedExpensesIdsHandler.getValue().mapNotNull {
            _expenses.value.find { expense -> expense.id == it }
        }.toList()
    }
}