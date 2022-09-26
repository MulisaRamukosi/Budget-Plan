package com.puzzle.industries.budgetplan.viewModels.registrationFlow

import com.puzzle.industries.budgetplan.R
import com.puzzle.industries.budgetplan.data.CountryCurrency
import com.puzzle.industries.budgetplan.viewModels.custom.PubSubViewModel
import dagger.hilt.android.lifecycle.HiltViewModel
import javax.inject.Inject

@HiltViewModel
class CurrencyViewModel @Inject constructor() : PubSubViewModel<Int>(initial = 0) {
    val countries: List<CountryCurrency> = listOf(
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
}