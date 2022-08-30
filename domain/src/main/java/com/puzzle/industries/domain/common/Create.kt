package com.puzzle.industries.domain.common

interface Create<T> {
    suspend fun create(vararg entity: T) : Boolean
}