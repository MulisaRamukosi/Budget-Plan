package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.data.CountryCurrency
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor() : ViewModel() {
    private val countries: List<CountryCurrency> = listOf(
        CountryCurrency(
            currency = "ZAR",
            country = "South Africa",
            symbol = "R",
            flagId = R.drawable.ic_flag_south_africa
        ),
        CountryCurrency(
            currency = "RWF",
            country = "Rwanda",
            symbol = "R₣",
            flagId = R.drawable.ic_flag_rwanda
        ),
        CountryCurrency(
            currency = "LSL",
            country = "Lesotho",
            symbol = "L",
            flagId = R.drawable.ic_flag_lesotho
        )
    )

    private val selectedCountry: MutableLiveData<CountryCurrency> by lazy {
        MutableLiveData(countries[0])
    }

    fun getAllFlags(): List<CountryCurrency> {
        return countries
    }

    fun getSelectedCountry(): LiveData<CountryCurrency> {
        return selectedCountry
    }

    fun getDefaultCountry(): CountryCurrency {
        return countries[0]
    }

    fun selectCountry(i: Int) {
        selectedCountry.value = countries[i]
    }
}