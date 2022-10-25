package com.puzzle.industries.budgetplan.viewModels.budget.reminder

import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CrudViewModelHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.CurrencySymbolObserverDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CrudViewModelHandlerDelegateImpl
import com.puzzle.industries.budgetplan.delegates.implementation.CurrencySymbolObserverDelegateImpl
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.reminder.ReminderWithExpense
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.models.reminder.Reminder
import com.puzzle.industries.domain.usescases.reminder.ReminderUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import javax.inject.Inject

@HiltViewModel
class ReminderViewModel @Inject constructor(
    private val reminderUseCase: ReminderUseCase,
    private val currencyPreferenceService: CountryCurrencyDataStore
) : ViewModel(),
    CurrencySymbolObserverDelegate by CurrencySymbolObserverDelegateImpl(currencyPreferenceService),
    CrudViewModelHandlerDelegate<Boolean, ReminderWithExpense> by CrudViewModelHandlerDelegateImpl(),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    val currencySymbol: StateFlow<String> = currencySymbolFlow

    val reminders: StateFlow<List<ReminderWithExpense>> = items.asStateFlow()
    val crudResponseEventListener: SharedFlow<Response<Boolean>> = crudResponseEventEmitter

    init {
        initRemindersWithExpense()
    }

    private fun initRemindersWithExpense() = runCoroutine {
        val response: Response<Flow<List<ReminderWithExpense>>> = reminderUseCase.read.readAll()
        response.response.distinctUntilChanged().collect { reminderWithExpenses ->
            items.value = reminderWithExpenses
        }
    }

    fun saveReminders(vararg expenses: Expense) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = reminderUseCase.create.create(
                expenses = expenses
            )
        )
    }

    fun deleteReminders(vararg reminders: Reminder) = runCoroutine {
        reminderUseCase.delete.delete(reminders = reminders)
    }

    fun updateReminders(vararg reminderWithExpenses: ReminderWithExpense) = runCoroutine {
        reminderUseCase.update.update(reminders = reminderWithExpenses)
    }
}