package com.puzzle.industries.domain.models.expense

import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.BaseHistory
import java.util.*

data class ExpenseHistory (
    override val id: UUID = UUID.randomUUID(),
    val expense: Expense,
    override val action: Action,
    override val reason: String,
    override val entryDate: Date = Date()
) : BaseHistory()