package com.puzzle.industries.domain.models

import com.puzzle.industries.domain.constants.Action
import java.util.*

abstract class BaseHistory {
    abstract val id: UUID
    abstract val action: Action
    abstract val reason: String
    abstract val entryDate: Date
}