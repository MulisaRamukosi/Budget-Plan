package com.puzzle.industries.data.storage.database.dao.base

import kotlinx.coroutines.flow.Flow

internal interface Read<T> {

    fun read(): Flow<List<T>>
}