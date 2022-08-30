package com.puzzle.industries.domain.common

import kotlinx.coroutines.flow.Flow

interface ReadAll<T> {
    fun readAll() : Flow<List<T>>
}