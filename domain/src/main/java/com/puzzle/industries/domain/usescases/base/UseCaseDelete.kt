package com.puzzle.industries.domain.usescases.base

import com.puzzle.industries.domain.common.response.Response

interface UseCaseDelete<T> {
    suspend fun delete(reason: String, vararg entity: T): Response<Boolean>
}