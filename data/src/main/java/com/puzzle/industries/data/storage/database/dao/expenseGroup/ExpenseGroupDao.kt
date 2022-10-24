package com.puzzle.industries.data.storage.database.dao.expenseGroup

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.puzzle.industries.data.storage.database.constants.Entities
import com.puzzle.industries.data.storage.database.dao.base.*
import com.puzzle.industries.data.storage.database.entity.expenseGroup.ExpenseGroupEntity
import com.puzzle.industries.data.storage.database.entity.expenseGroup.ExpenseGroupWithExpensesEntity
import kotlinx.coroutines.flow.Flow

@Dao
internal interface ExpenseGroupDao : Insert<ExpenseGroupEntity>, Update<ExpenseGroupEntity>,
    Delete<ExpenseGroupEntity>, Read<ExpenseGroupWithExpensesEntity> {

    @Transaction
    @Query("select * from ${Entities.EXPENSE_GROUP}")
    override fun read(): Flow<List<ExpenseGroupWithExpensesEntity>>

}