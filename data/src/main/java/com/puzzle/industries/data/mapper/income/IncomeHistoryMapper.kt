package com.puzzle.industries.data.mapper.income

import com.puzzle.industries.data.storage.database.entity.income.IncomeHistoryEntity
import com.puzzle.industries.domain.models.income.IncomeHistory
import javax.inject.Inject

internal class IncomeHistoryMapper @Inject constructor(private val incomeMapper: IncomeMapper) {

    fun toIncomeHistoryEntity(incomeHistory: IncomeHistory): IncomeHistoryEntity{
        return IncomeHistoryEntity(
            id = incomeHistory.id,
            income = incomeMapper.toIncomeEntity(incomeHistory.income),
            reason = incomeHistory.reason,
            action = incomeHistory.action
        )
    }

    fun toIncomeHistory(incomeHistory: IncomeHistoryEntity) : IncomeHistory{
        return IncomeHistory(
            id = incomeHistory.id,
            income = incomeMapper.toIncome(incomeHistory.income),
            reason = incomeHistory.reason,
            action = incomeHistory.action,
            entryDate = incomeHistory.entryDate
        )
    }
}