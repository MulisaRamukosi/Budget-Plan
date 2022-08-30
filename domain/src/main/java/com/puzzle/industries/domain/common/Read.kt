package com.puzzle.industries.domain.common

import kotlinx.coroutines.flow.Flow

interface Read<T> {
    fun read() : Flow<List<T>>
}