package com.puzzle.industries.data.database.entity

import java.util.*

abstract class BaseHistory {
    abstract val id: UUID
    abstract val action: String
    abstract val reason: String
    abstract val entryDate: Date
}