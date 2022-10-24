package com.puzzle.industries.data.storage.database.entity.reminder

import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import com.puzzle.industries.data.storage.database.constants.Entities
import com.puzzle.industries.data.storage.database.entity.expense.ExpenseEntity
import java.util.*

@Entity(
    tableName = Entities.REMINDER,
    foreignKeys = [
        ForeignKey(
            entity = ExpenseEntity::class,
            parentColumns = arrayOf("id"),
            childColumns = arrayOf("expenseId"),
            onDelete = ForeignKey.CASCADE
        )
    ],
    indices = [
        Index("expenseId")
    ]
)
internal class ReminderEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int,
    val expenseId: UUID,
    val remind: Boolean
)