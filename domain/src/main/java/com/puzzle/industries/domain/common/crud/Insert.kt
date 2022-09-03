package com.puzzle.industries.domain.common.crud

import com.puzzle.industries.domain.common.response.Response

interface Insert<T> {
    suspend fun insert(vararg entity: T) : Response<Boolean>
}