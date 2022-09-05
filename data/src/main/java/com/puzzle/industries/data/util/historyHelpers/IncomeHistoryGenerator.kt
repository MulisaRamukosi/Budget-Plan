package com.puzzle.industries.data.util.historyHelpers

import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.models.income.IncomeHistory

internal class IncomeHistoryGenerator : HistoryGeneratorDelegate<Income, IncomeHistory> {
    override fun generateHistory(
        reason: String,
        action: Action,
        vararg entity: Income
    ): Array<IncomeHistory> =
        entity.map { IncomeHistory(income = it, reason = reason, action = action) }.toTypedArray()
}