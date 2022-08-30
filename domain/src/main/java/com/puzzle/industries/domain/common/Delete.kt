package com.puzzle.industries.domain.common

interface Delete<T> {
    suspend fun delete(entity: T): Boolean
}