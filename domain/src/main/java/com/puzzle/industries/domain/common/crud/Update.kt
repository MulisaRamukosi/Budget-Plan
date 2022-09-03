package com.puzzle.industries.domain.common.crud

import com.puzzle.industries.domain.common.response.Response

interface Update<T> {
    suspend fun update(entity: T) : Response<Boolean>
}