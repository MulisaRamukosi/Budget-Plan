package com.puzzle.industries.domain.models.expenseGroup

import com.puzzle.industries.domain.constants.Action
import com.puzzle.industries.domain.models.BaseHistory
import java.util.*

data class ExpenseGroupHistory(
    override val id: UUID,
    val oldName: String,
    val newName: String,
    override val action: Action,
    override val reason: String,
    override val entryDate: Date
) : BaseHistory()