package com.puzzle.industries.data.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.database.dao.base.Insert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal abstract class BaseDaoInsertTest<Dao : Insert<E>, E>(val testEntities: Array<E>) :
    BaseDaoTest<Dao>() {

    @Test
    fun insertEntity_dbHasNoEntity_InsertSuccess() = runTest {
        val entity = dao.insert(*testEntities.copyOfRange(0, 1))

        assertEquals(1, entity.size)
    }

    @Test
    fun insertEntities_dbHasNoEntities_InsertSuccess() = runTest {
        val entities = dao.insert(*testEntities)

        assertEquals(2, entities.size)
    }

}