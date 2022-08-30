package com.puzzle.industries.data.database.dao.base

import kotlinx.coroutines.flow.Flow

internal interface Read<T> {

    fun read(): Flow<List<T>>
}