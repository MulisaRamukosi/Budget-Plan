package com.puzzle.industries.budgetplan.data

import androidx.annotation.DrawableRes

data class CountryCurrency (
    val country: String,
    val currency: String,
    val symbol: String,
    @DrawableRes val flagId: Int,

)