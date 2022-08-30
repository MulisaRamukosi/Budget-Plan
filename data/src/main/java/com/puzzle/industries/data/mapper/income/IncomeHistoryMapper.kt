package com.puzzle.industries.data.mapper.income

import com.puzzle.industries.data.database.entity.income.IncomeHistoryEntity
import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.income.IncomeHistory

internal class IncomeHistoryMapper {

    fun toIncomeHistoryEntity(incomeHistory: IncomeHistory): IncomeHistoryEntity{
        return IncomeHistoryEntity(
            id = incomeHistory.id,
            oldAmount = incomeHistory.oldAmount,
            newAmount = incomeHistory.newAmount,
            oldFrequency = incomeHistory.oldFrequency.name,
            newFrequency = incomeHistory.newFrequency.name,
            oldTitle = incomeHistory.oldTitle,
            newTitle = incomeHistory.newTitle,
            reason = incomeHistory.reason,
            action = incomeHistory.action.name
        )
    }

    fun toIncomeHistory(incomeHistory: IncomeHistoryEntity) : IncomeHistory{
        return IncomeHistory(
            id = incomeHistory.id,
            oldAmount = incomeHistory.oldAmount,
            newAmount = incomeHistory.newAmount,
            oldFrequency = Frequency.valueOf(incomeHistory.oldFrequency),
            newFrequency = Frequency.valueOf(incomeHistory.newFrequency),
            oldTitle = incomeHistory.oldTitle,
            newTitle = incomeHistory.newTitle,
            reason = incomeHistory.reason,
            action = Action.valueOf(incomeHistory.action),
            entryDate = incomeHistory.entryDate
        )
    }
}