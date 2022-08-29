package com.puzzle.industries.data.database.dao.expense

import androidx.room.Dao
import androidx.room.Query
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.dao.base.Delete
import com.puzzle.industries.data.database.dao.base.Insert
import com.puzzle.industries.data.database.dao.base.ReadAll
import com.puzzle.industries.data.database.dao.base.Update
import com.puzzle.industries.data.database.entity.expense.ExpenseHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseHistoryDao : Insert<ExpenseHistoryEntity>, Update<ExpenseHistoryEntity>,
    Delete<ExpenseHistoryEntity>, ReadAll<ExpenseHistoryEntity> {

    @Query("select * from ${Entities.EXPENSE_HISTORY}")
    override fun readAll(): Flow<List<ExpenseHistoryEntity>>
}