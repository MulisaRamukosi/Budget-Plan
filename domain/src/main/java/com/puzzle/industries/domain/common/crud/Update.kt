package com.puzzle.industries.domain.common.crud

import com.puzzle.industries.domain.common.response.Response

interface Update<T> {
    suspend fun update(vararg entity: T) : Response<Boolean>
}