package com.puzzle.industries.domain.common.crud

import com.puzzle.industries.domain.common.response.Response
import kotlinx.coroutines.flow.Flow

interface Read<T> {
    fun read() : Response<Flow<List<T>>>
}