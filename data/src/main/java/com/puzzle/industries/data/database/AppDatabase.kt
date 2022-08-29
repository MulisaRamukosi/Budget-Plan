package com.puzzle.industries.data.database

import android.content.Context
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.puzzle.industries.data.database.dao.expense.ExpenseDao
import com.puzzle.industries.data.database.dao.expense.ExpenseHistoryDao
import com.puzzle.industries.data.database.dao.expenseGroup.ExpenseGroupDao
import com.puzzle.industries.data.database.dao.expenseGroup.ExpenseGroupHistoryDao
import com.puzzle.industries.data.database.dao.income.IncomeDao
import com.puzzle.industries.data.database.dao.income.IncomeHistoryDao
import com.puzzle.industries.data.database.entity.expense.ExpenseEntity
import com.puzzle.industries.data.database.entity.expense.ExpenseHistoryEntity
import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupEntity
import com.puzzle.industries.data.database.entity.expenseGroup.ExpenseGroupHistoryEntity
import com.puzzle.industries.data.database.entity.income.IncomeEntity
import com.puzzle.industries.data.database.entity.income.IncomeHistoryEntity
import com.puzzle.industries.data.database.typeConverters.DateConverter
import com.puzzle.industries.data.database.typeConverters.FrequencyConverter

@Database(
    entities = [
        ExpenseEntity::class,
        ExpenseHistoryEntity::class,
        ExpenseGroupEntity::class,
        ExpenseGroupHistoryEntity::class,
        IncomeEntity::class,
        IncomeHistoryEntity::class
    ],
    version = 1,
    exportSchema = false
)
@TypeConverters(
    value = [
        DateConverter::class,
        FrequencyConverter::class
    ]
)
abstract class AppDatabase : RoomDatabase() {

    abstract fun incomeDao(): IncomeDao
    abstract fun incomeHistoryDao(): IncomeHistoryDao

    abstract fun expenseDao(): ExpenseDao
    abstract fun expenseHistoryDao(): ExpenseHistoryDao

    abstract fun expenseGroupDao(): ExpenseGroupDao
    abstract fun expenseGroupHistoryDao(): ExpenseGroupHistoryDao

    companion object {
        private const val DB_NAME = "budget-plan"
        fun buildDatabase(context: Context): AppDatabase =
            Room.databaseBuilder(context, AppDatabase::class.java, DB_NAME).build()
    }
}