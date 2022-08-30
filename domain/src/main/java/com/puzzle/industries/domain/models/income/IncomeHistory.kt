package com.puzzle.industries.domain.models.income

import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.BaseHistory
import java.util.*

class IncomeHistory(
    override val id: UUID,
    val oldAmount: Double,
    val newAmount: Double,
    val oldFrequency: Frequency,
    val newFrequency: Frequency,
    val oldTitle: String,
    val newTitle: String,
    override val reason: String,
    override val action: Action,
    override val entryDate: Date
) : BaseHistory()
