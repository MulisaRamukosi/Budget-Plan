package com.puzzle.industries.data.database.dao.base

import androidx.room.Delete


interface Delete<T> {
    @Delete
    suspend fun delete(entity: T): Int
}