package com.puzzle.industries.domain.usescases.base

import com.puzzle.industries.domain.common.response.Response
import kotlinx.coroutines.flow.Flow

interface UseCaseRead<T> {
    fun readAll(): Response<Flow<List<T>>>
}