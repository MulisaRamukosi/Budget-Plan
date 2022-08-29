package com.puzzle.industries.data.database.dao.base

import kotlinx.coroutines.flow.Flow

interface ReadAll<T> {

    fun readAll(): Flow<List<T>>
}