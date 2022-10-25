package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.data.CountryCurrency
import com.puzzle.industries.budgetplan.delegates.CoroutineHandlerDelegate
import com.puzzle.industries.budgetplan.delegates.implementation.CoroutineHandlerDelegateImpl

import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.datastores.BPlanGenDayDataStore
import com.puzzle.industries.domain.datastores.CountryCurrencyDataStore
import com.puzzle.industries.domain.datastores.DebtDataStore
import com.puzzle.industries.domain.datastores.LaunchTrackingDataStore
import com.puzzle.industries.domain.usescases.income.IncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class RegistrationFlowViewModel @Inject constructor(
    private val incomeUseCase: IncomeUseCase,
    private val countryCurrencyDataStore: CountryCurrencyDataStore,
    private val debtDataStore: DebtDataStore,
    private val launchTrackingDataStore: LaunchTrackingDataStore,
    private val bPlanGenDayDataStore: BPlanGenDayDataStore
) : ViewModel(), CoroutineHandlerDelegate by CoroutineHandlerDelegateImpl() {

    private lateinit var countryCurrency: CountryCurrency
    private lateinit var income: Income
    private var day: Int = 1
    private var debtAllowed: Boolean = false


    fun register(){
        runCoroutine {
            incomeUseCase.create.insert(reason = "", income)
            countryCurrencyDataStore.saveCurrencySymbol(symbol = countryCurrency.symbol)
            bPlanGenDayDataStore.saveDay(day = day)
            debtDataStore.saveAllowDebtOption(option = debtAllowed)
            launchTrackingDataStore.updateToNotFirstTimeLaunch()
        }
    }

    fun setCurrency(countryCurrency: CountryCurrency){
        this.countryCurrency = countryCurrency
    }

    fun setIncome(income: Income){
        this.income = income
    }

    fun setDebtAllowed(debtAllowed: Boolean){
        this.debtAllowed = debtAllowed
    }

    fun setBudgetPlanGenerationDay(day: Int) {
        this.day = day
    }
}