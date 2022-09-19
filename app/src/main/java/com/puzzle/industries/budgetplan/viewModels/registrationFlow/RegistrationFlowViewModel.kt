package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import androidx.compose.ui.res.stringResource
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.puzzle.industries.budgetplan.data.CountryCurrency

import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.services.CountryCurrencyPreferenceService
import com.puzzle.industries.domain.services.DebtPreferenceService
import com.puzzle.industries.domain.services.LaunchTrackingPreferenceService
import com.puzzle.industries.domain.usescases.income.IncomeUseCase
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class RegistrationFlowViewModel @Inject constructor(
    private val incomeUseCase: IncomeUseCase,
    private val countryCurrencyPreferenceService: CountryCurrencyPreferenceService,
    private val debtPreferenceService: DebtPreferenceService,
    private val launchTrackingPreferenceService: LaunchTrackingPreferenceService
) : ViewModel() {

    private lateinit var countryCurrency: CountryCurrency
    private lateinit var income: Income
    private var debtAllowed: Boolean = false


    fun register(){
        viewModelScope.launch {
            incomeUseCase.create.insert(reason = "", income)
        }

        countryCurrencyPreferenceService.saveCurrencySymbol(symbol = countryCurrency.symbol)
        debtPreferenceService.saveAllowDebtOption(option = debtAllowed)
        launchTrackingPreferenceService.updateToNotFirstTimeLaunch()
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
}