package com.puzzle.industries.domain.common

interface MultiDelete<T> {
    suspend fun delete(entities: List<T>) : Boolean
}