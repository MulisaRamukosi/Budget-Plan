package com.puzzle.industries.data.database.dao.expense

import androidx.room.Dao
import androidx.room.Query
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.dao.base.*
import com.puzzle.industries.data.database.entity.expense.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ExpenseDao : Insert<ExpenseEntity>, Update<ExpenseEntity>, Delete<ExpenseEntity>,
    Read<ExpenseEntity> {

    @Query("select * from ${Entities.EXPENSE}")
    override fun read(): Flow<List<ExpenseEntity>>
}