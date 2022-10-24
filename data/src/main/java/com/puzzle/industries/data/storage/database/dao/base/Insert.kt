package com.puzzle.industries.data.storage.database.dao.base

import androidx.room.Insert


internal interface Insert<T> {
    @Insert
    suspend fun insert(vararg entity: T): List<Long>
}