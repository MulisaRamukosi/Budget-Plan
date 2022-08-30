package com.puzzle.industries.data.database.dao.income

import androidx.room.Dao
import androidx.room.Query
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.dao.base.Delete
import com.puzzle.industries.data.database.dao.base.Insert
import com.puzzle.industries.data.database.dao.base.Read
import com.puzzle.industries.data.database.dao.base.Update
import com.puzzle.industries.data.database.entity.income.IncomeEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface IncomeDao : Insert<IncomeEntity>, Update<IncomeEntity>, Delete<IncomeEntity>,
    Read<IncomeEntity> {

    @Query("select * from ${Entities.INCOME}")
    override fun read(): Flow<List<IncomeEntity>>

}