package com.puzzle.industries.data.database.entity

import java.util.*

internal abstract class BaseHistory {
    abstract val id: UUID
    abstract val action: String
    abstract val reason: String
    abstract val entryDate: Date
}