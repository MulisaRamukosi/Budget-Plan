package com.puzzle.industries.domain.common.crud

import com.puzzle.industries.domain.common.response.Response

interface Create<T> {
    suspend fun create(vararg entity: T) : Response<Boolean>
}