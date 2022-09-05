package com.puzzle.industries.domain.usescases.base

import com.puzzle.industries.domain.common.response.Response

interface UseCaseUpdate<T> {
    suspend fun update(reason: String, vararg entity: T) : Response<Boolean>
}