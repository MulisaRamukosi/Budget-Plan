package com.puzzle.industries.domain.models.income

import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.BaseHistory
import java.util.*

class IncomeHistory(
    override val id: UUID = UUID.randomUUID(),
    val income: Income,
    override val reason: String,
    override val action: Action,
    override val entryDate: Date = Date()
) : BaseHistory()
