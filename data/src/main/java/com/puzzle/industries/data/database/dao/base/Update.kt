package com.puzzle.industries.data.database.dao.base

import androidx.room.OnConflictStrategy
import androidx.room.Update

internal interface Update<T> {
    @Update(onConflict = OnConflictStrategy.REPLACE)
    suspend fun update(entity: T): Int
}