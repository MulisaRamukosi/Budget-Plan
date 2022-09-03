package com.puzzle.industries.data.database.dao.base

import androidx.room.Delete


internal interface Delete<T> {
    @Delete
    suspend fun delete(vararg entity: T): Int
}