package com.puzzle.industries.domain.common

interface Update<T> {
    suspend fun update(entity: T) : Boolean
}