package com.puzzle.industries.data.repo

import android.content.Context
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import com.puzzle.industries.data.storage.database.AppDatabase
import com.puzzle.industries.data.util.ResponseMessageFactory
import org.junit.After
import org.junit.Before
import java.io.IOException

internal abstract class BaseRepoTest<Repo : Any> {

    protected lateinit var responseMessageFactory: ResponseMessageFactory
    protected lateinit var db: AppDatabase
    protected lateinit var repo: Repo

    @Before
    open fun createDb(){
        val context = ApplicationProvider.getApplicationContext<Context>()
        db = Room.inMemoryDatabaseBuilder(context, AppDatabase::class.java).build()
        responseMessageFactory = ResponseMessageFactory(context)
        repo = initRepo()
    }

    abstract fun initRepo(): Repo

    @After
    @Throws(IOException::class)
    fun closeDb() {
        db.close()
    }
}