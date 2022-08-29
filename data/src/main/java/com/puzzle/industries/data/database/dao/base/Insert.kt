package com.puzzle.industries.data.database.dao.base

import androidx.room.Insert
import androidx.room.OnConflictStrategy


interface Insert<T> {
    @Insert
    suspend fun insert(entity: T): Long
}