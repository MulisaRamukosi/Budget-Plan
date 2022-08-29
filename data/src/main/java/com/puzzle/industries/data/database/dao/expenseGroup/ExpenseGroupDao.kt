package com.puzzle.industries.data.database.dao.expenseGroup

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.puzzle.industries.data.database.constants.Entities
import com.puzzle.industries.data.database.dao.base.Delete
import com.puzzle.industries.data.database.dao.base.Insert
import com.puzzle.industries.data.database.dao.base.ReadAll
import com.puzzle.industries.data.database.dao.base.Update
import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupEntity
import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupWithExpensesEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface ExpenseGroupDao : Insert<ExpenseGroupEntity>, Update<ExpenseGroupEntity>,
    Delete<ExpenseGroupEntity>, ReadAll<ExpenseGroupWithExpensesEntity> {

    @Transaction
    @Query("select * from ${Entities.EXPENSE_GROUP}")
    override fun readAll(): Flow<List<ExpenseGroupWithExpensesEntity>>

}