package com.puzzle.industries.domain.common

interface Create<T> {
    suspend fun create(entity: T)
}