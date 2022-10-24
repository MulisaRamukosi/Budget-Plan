package com.puzzle.industries.data.storage.database.entity

import com.puzzle.industries.domain.constants.Action
import java.util.*

internal abstract class BaseHistory {
    abstract val id: UUID
    abstract val action: Action
    abstract val reason: String
    abstract val entryDate: Date
}