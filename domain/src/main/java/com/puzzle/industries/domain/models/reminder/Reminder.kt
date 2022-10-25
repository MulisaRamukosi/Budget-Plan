package com.puzzle.industries.domain.models.reminder

import java.util.UUID

data class Reminder(
    var id: Int,
    val expenseId: UUID,
    var remind: Boolean
)
