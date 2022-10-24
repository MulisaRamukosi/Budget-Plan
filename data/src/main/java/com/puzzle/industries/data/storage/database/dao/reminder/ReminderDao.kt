package com.puzzle.industries.data.storage.database.dao.reminder

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import com.puzzle.industries.data.storage.database.constants.Entities
import com.puzzle.industries.data.storage.database.dao.base.Delete
import com.puzzle.industries.data.storage.database.dao.base.Insert
import com.puzzle.industries.data.storage.database.dao.base.Read
import com.puzzle.industries.data.storage.database.dao.base.Update
import com.puzzle.industries.data.storage.database.entity.reminder.ReminderEntity
import com.puzzle.industries.data.storage.database.entity.reminder.ReminderWithExpenseEntity
import kotlinx.coroutines.flow.Flow
import java.util.*

@Dao
internal interface ReminderDao : Insert<ReminderEntity>, Read<ReminderWithExpenseEntity>,
    Delete<ReminderEntity>, Update<ReminderEntity> {

    @Transaction
    @Query("select * from ${Entities.REMINDER}")
    override fun read(): Flow<List<ReminderWithExpenseEntity>>

    @Transaction
    @Query("select * from ${Entities.REMINDER} where expenseId in (:expenseIds) and remind")
    fun getEnabledRemindersByExpenseIds(vararg expenseIds: UUID): List<ReminderWithExpenseEntity>

    @Query("select id from ${Entities.REMINDER} where expenseId in (:expenseIds)")
    fun getReminderIdByExpenseId(vararg expenseIds: UUID): Flow<List<Int>>

    @Query("delete from ${Entities.REMINDER} where expenseId in (:expenseIds)")
    fun deleteRemindersByExpenseIds(vararg expenseIds: UUID): Int
}