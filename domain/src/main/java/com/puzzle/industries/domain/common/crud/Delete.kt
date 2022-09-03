package com.puzzle.industries.domain.common.crud

import com.puzzle.industries.domain.common.response.Response

interface Delete<T> {
    suspend fun delete(vararg entity: T): Response<Boolean>
}