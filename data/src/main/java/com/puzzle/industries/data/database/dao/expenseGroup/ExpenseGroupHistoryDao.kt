package com.puzzle.industries.data.database.dao.expenseGroup

import androidx.room.Dao
import androidx.room.Query
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.dao.base.Delete
import com.puzzle.industries.data.database.dao.base.Insert
import com.puzzle.industries.data.database.dao.base.ReadAll
import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseGroupHistoryDao : Insert<ExpenseGroupHistoryEntity>,
    Delete<ExpenseGroupHistoryEntity>, ReadAll<ExpenseGroupHistoryEntity> {

    @Query("select * from ${Entities.EXPENSE_GROUP_HISTORY}")
    override fun readAll(): Flow<List<ExpenseGroupHistoryEntity>>
}