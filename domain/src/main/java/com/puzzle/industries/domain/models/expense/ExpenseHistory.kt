package com.puzzle.industries.domain.models.expense

import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.constants.Frequency
import com.puzzle.industries.domain.models.BaseHistory
import java.util.*

data class ExpenseHistory (
    override val id: UUID,
    val expenseGroupId: String,
    val oldName: String,
    val newName: String,
    val oldAmount: Double,
    val newAmount: Double,
    val oldFrequency: Frequency,
    val newFrequency: Frequency,
    override val action: Action,
    override val reason: String,
    override val entryDate: Date
) : BaseHistory()