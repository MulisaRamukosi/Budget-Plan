package com.puzzle.industries.budgetplan.viewModels.budget.expenses

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
import com.puzzle.industries.domain.models.expense.Expense
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses
import com.puzzle.industries.domain.services.MonthTotalAmountCalculatorService
import com.puzzle.industries.domain.usescases.expense.ExpenseUseCase
import com.puzzle.industries.domain.usescases.expenseGroup.ExpenseGroupUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.*
import java.util.*
import javax.inject.Inject

@HiltViewModel
class ExpenseViewModel @Inject constructor(
    private val expenseGroupUseCase: ExpenseGroupUseCase,
    private val expenseUseCase: ExpenseUseCase,
    private val currencyPreferenceService: CountryCurrencyDataStore,
    private val monthTotalCalculator: MonthTotalAmountCalculatorService
) : ViewModel(),
    CurrencySymbolObserverDelegate by CurrencySymbolObserverDelegateImpl(currencyPreferenceService),
    CrudViewModelHandlerDelegate<Boolean, ExpenseGroupWithExpenses> by CrudViewModelHandlerDelegateImpl(),
    CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    val expenseGroupsWithExpenses = items.asStateFlow()

    val deleteExpenseGroupWithExpensesEventListener: SharedFlow<ExpenseGroupWithExpenses> =
        deleteValueEventEmitter
    val onDeleteExpenseGroupWithExpenses: (ExpenseGroupWithExpenses) -> Unit = onDeleteValue

    private val _deleteExpenseEventEmitter by lazy {
        MutableSharedFlow<Expense>()
    }
    val deleteExpenseEventListener: SharedFlow<Expense> = _deleteExpenseEventEmitter
    val onDeleteExpense: (Expense) -> Unit =
        { runCoroutine { _deleteExpenseEventEmitter.emit(it) } }

    val updateExpenseEventListener: SharedFlow<Unit> = updateValueEventEmitter
    val emitUpdateExpenseEvent: () -> Unit = onUpdateValue

    val currencySymbol: StateFlow<String> = currencySymbolFlow

    val crudResponseEventListener: SharedFlow<Response<Boolean>> = crudResponseEventEmitter

    private val _totalExpenses = MutableStateFlow(value = 0.0)
    val totalExpenses = _totalExpenses

    private val _totalExpenseForMonth = MutableStateFlow(value = 0.0)
    val totalExpenseForMonth: StateFlow<Double> = _totalExpenseForMonth

    init {
        initExpenseGroupWithExpenses()
        initTotalExpenses()
        initTotalExpensesForMonth()
    }

    private fun initExpenseGroupWithExpenses() = runCoroutine {
        val response: Response<Flow<List<ExpenseGroupWithExpenses>>> =
            expenseGroupUseCase.read.readAll()

        response.response.distinctUntilChanged().collect { expenseGroupWithExpenses ->
            items.value = expenseGroupWithExpenses
        }
    }

    private fun initTotalExpenses() = runCoroutine {
        expenseGroupsWithExpenses.collect { expenseGroupsWithExpenses ->
            _totalExpenses.value = expenseGroupsWithExpenses.sumOf { expenseGroupWithExpenses ->
                expenseGroupWithExpenses.expenses.sumOf { expense -> expense.amount }
            }
        }
    }

    private fun initTotalExpensesForMonth() = runCoroutine {
        expenseGroupsWithExpenses.collect { expenseGroupsWithExpenses ->
            val expenses = expenseGroupsWithExpenses.flatMap { it.expenses }
            _totalExpenseForMonth.value =
                monthTotalCalculator.calculateTotalExpensesForMonth(expenses = expenses)
        }
    }

    fun getTotalExpensesForMonth(month: Int) = runCoroutine {
        expenseGroupsWithExpenses.collectLatest { expenseGroupsWithExpenses ->
            val expenses = expenseGroupsWithExpenses.flatMap { it.expenses }
            _totalExpenseForMonth.value = monthTotalCalculator.calculateTotalExpensesForMonth(
                month = Months.values()[month],
                expenses = expenses
            )
        }
    }

    fun getExpenseGroupById(expenseGroupId: UUID): ExpenseGroup? =
        expenseGroupsWithExpenses.value.find { expenseGroupWithExpenses ->
            expenseGroupWithExpenses.expenseGroup.id == expenseGroupId
        }?.expenseGroup

    fun getExpenseById(expenseId: UUID): Expense? =
        expenseGroupsWithExpenses.value.flatMap { expenseGroupsWithExpenses -> expenseGroupsWithExpenses.expenses }
            .find { expense ->
                expense.id == expenseId
            }


    fun getExpenseGroupWithExpensesById(expenseGroupId: UUID): ExpenseGroupWithExpenses? =
        expenseGroupsWithExpenses.value.find { expenseGroupWithExpenses ->
            expenseGroupWithExpenses.expenseGroup.id == expenseGroupId
        }

    fun saveExpenseGroup(reason: String, vararg expenseGroups: ExpenseGroup) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = expenseGroupUseCase.create.insert(
                reason = reason,
                entity = expenseGroups
            )
        )
    }

    fun updateExpenseGroup(reason: String, vararg expenseGroups: ExpenseGroup) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = expenseGroupUseCase.update.update(
                reason = reason,
                entity = expenseGroups
            )
        )
    }

    fun deleteExpenseGroup(reason: String, vararg expenseGroups: ExpenseGroup) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = expenseGroupUseCase.delete.delete(
                reason = reason,
                entity = expenseGroups
            )
        )
    }

    fun saveExpenses(reason: String, vararg expenses: Expense) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = expenseUseCase.create.insert(
                reason = reason,
                entity = expenses
            )
        )
    }

    fun updateExpenses(reason: String, vararg expenses: Expense) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = expenseUseCase.update.update(
                reason = reason,
                entity = expenses
            )
        )
    }

    fun deleteExpenses(reason: String, vararg expenses: Expense) = runCoroutine {
        crudResponseEventEmitter.emit(
            value = expenseUseCase.delete.delete(
                reason = reason,
                entity = expenses
            )
        )
    }

    fun deleteExpenseGroupWithExpenses(
        reason: String,
        expenseGroupWithExpenses: ExpenseGroupWithExpenses
    ) = runCoroutine {
        val expenseGroup = expenseGroupWithExpenses.expenseGroup
        val expenses = expenseGroupWithExpenses.expenses.toTypedArray()

        if (expenses.isNotEmpty()) {
            val deleteExpensesResponse = expenseUseCase.delete.delete(
                reason = reason,
                *expenses
            )

            if (!deleteExpensesResponse.response) {
                crudResponseEventEmitter.emit(deleteExpensesResponse)
                return@runCoroutine
            }
        }

        crudResponseEventEmitter.emit(
            expenseGroupUseCase.delete.delete(
                reason = reason,
                expenseGroup
            )
        )
    }

}