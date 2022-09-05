package com.puzzle.industries.domain.usescases.base

import com.puzzle.industries.domain.common.response.Response

interface UseCaseInsert<T> {
    suspend fun insert(reason: String, vararg entity: T): Response<Boolean>
}