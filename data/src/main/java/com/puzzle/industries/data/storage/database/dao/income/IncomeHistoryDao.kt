package com.puzzle.industries.data.storage.database.dao.income

import androidx.room.Dao
import androidx.room.Query
import com.puzzle.industries.data.storage.database.constants.Entities
import com.puzzle.industries.data.storage.database.dao.base.Delete
import com.puzzle.industries.data.storage.database.dao.base.Insert
import com.puzzle.industries.data.storage.database.dao.base.Read
import com.puzzle.industries.data.storage.database.entity.income.IncomeHistoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface IncomeHistoryDao : Insert<IncomeHistoryEntity>, Delete<IncomeHistoryEntity>,
    Read<IncomeHistoryEntity> {

    @Query("select * from ${Entities.INCOME_HISTORY}")
    override fun read(): Flow<List<IncomeHistoryEntity>>
}