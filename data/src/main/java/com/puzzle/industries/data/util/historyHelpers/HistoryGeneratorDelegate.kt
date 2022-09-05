package com.puzzle.industries.data.util.historyHelpers

import com.puzzle.industries.domain.constants.Action

internal interface HistoryGeneratorDelegate<I, T> {
    fun generateHistory(reason: String, action: Action, vararg entity: I) : Array<T>
}