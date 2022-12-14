package com.puzzle.industries.data.storage.database.dao.expenseGroup

import androidx.room.Dao
import androidx.room.Query
import com.puzzle.industries.data.storage.database.constants.Entities
import com.puzzle.industries.data.storage.database.dao.base.Delete
import com.puzzle.industries.data.storage.database.dao.base.Insert
import com.puzzle.industries.data.storage.database.dao.base.Read
import com.puzzle.industries.data.storage.database.entity.expenseGroup.ExpenseGroupHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ExpenseGroupHistoryDao : Insert<ExpenseGroupHistoryEntity>,
    Delete<ExpenseGroupHistoryEntity>, Read<ExpenseGroupHistoryEntity> {

    @Query("select * from ${Entities.EXPENSE_GROUP_HISTORY}")
    override fun read(): Flow<List<ExpenseGroupHistoryEntity>>
}