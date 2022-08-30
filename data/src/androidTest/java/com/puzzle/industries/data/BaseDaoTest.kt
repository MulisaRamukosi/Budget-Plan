package com.puzzle.industries.data

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.database.AppDatabase
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.After
import org.junit.Before
import org.junit.runner.RunWith
import java.io.IOException

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal abstract class BaseDaoTest<Dao: Any> {

    protected lateinit var db: AppDatabase
    protected lateinit var dao: Dao

    @Before
    open fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        dao = initDao()
    }

    abstract fun initDao(): Dao

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}