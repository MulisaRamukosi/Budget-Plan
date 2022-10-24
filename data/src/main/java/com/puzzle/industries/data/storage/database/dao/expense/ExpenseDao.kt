package com.puzzle.industries.data.storage.database.dao.expense

import androidx.room.Dao
import androidx.room.Query
import androidx.room.RewriteQueriesToDropUnusedColumns
import com.puzzle.industries.data.storage.database.constants.Entities
import com.puzzle.industries.data.storage.database.dao.base.Delete
import com.puzzle.industries.data.storage.database.dao.base.Insert
import com.puzzle.industries.data.storage.database.dao.base.Read
import com.puzzle.industries.data.storage.database.dao.base.Update
import com.puzzle.industries.data.storage.database.entity.expense.ExpenseEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ExpenseDao : Insert<ExpenseEntity>, Update<ExpenseEntity>, Delete<ExpenseEntity>,
    Read<ExpenseEntity> {

    @Query("select * from ${Entities.EXPENSE}")
    override fun read(): Flow<List<ExpenseEntity>>

    @Query("select ${Entities.EXPENSE}.* " +
            "from ${Entities.EXPENSE} " +
            "left join ${Entities.REMINDER} " +
            "on ${Entities.EXPENSE}.id == ${Entities.REMINDER}.expenseId " +
            "where ${Entities.REMINDER}.expenseId is null")
    fun getExpensesWithNoAlarms(): Flow<List<ExpenseEntity>>
}