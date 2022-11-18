package com.puzzle.industries.data.storage.database.dao.income

import androidx.room.Dao
import androidx.room.Query
import com.puzzle.industries.data.storage.database.constants.Entities
import com.puzzle.industries.data.storage.database.dao.base.Delete
import com.puzzle.industries.data.storage.database.dao.base.Insert
import com.puzzle.industries.data.storage.database.dao.base.Read
import com.puzzle.industries.data.storage.database.dao.base.Update
import com.puzzle.industries.data.storage.database.entity.income.IncomeEntity
import com.puzzle.industries.domain.constants.FrequencyType
import kotlinx.coroutines.flow.Flow

@Dao
internal interface IncomeDao : Insert<IncomeEntity>, Update<IncomeEntity>, Delete<IncomeEntity>,
    Read<IncomeEntity> {

    @Query("select * from ${Entities.INCOME}")
    override fun read(): Flow<List<IncomeEntity>>

    @Query("select * from ${Entities.INCOME} where frequencyType == :frequencyType")
    fun readAllIncomesByFrequencyType(frequencyType: FrequencyType): Flow<List<IncomeEntity>>

}