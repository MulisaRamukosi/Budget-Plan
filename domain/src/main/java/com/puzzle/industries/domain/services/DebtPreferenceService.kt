package com.puzzle.industries.domain.services

interface DebtPreferenceService {

    fun saveAllowDebtOption(option: Boolean)
    fun getSavedDebtOption(): Boolean

}