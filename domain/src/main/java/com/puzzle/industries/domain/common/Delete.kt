package com.puzzle.industries.domain.common

interface Delete<T> {
    suspend fun delete(vararg entity: T): Boolean
}