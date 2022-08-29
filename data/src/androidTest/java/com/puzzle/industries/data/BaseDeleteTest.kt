package com.puzzle.industries.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.database.dao.base.Delete
import com.puzzle.industries.data.database.dao.base.Insert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
abstract class BaseDeleteTest<Dao, E>(val testEntities: List<E>) :
    BaseDaoTest<Dao>() where Dao : Delete<E>, Dao : Insert<E> {

    @Test
    fun deleteEntity_EntityDoesNotExistInDb_ReturnsZero() = runTest {
        val result = dao.delete(testEntities[0])

        assertEquals(0, result)
    }

    @Test
    fun deleteEntity_EntityExistsInDb_ReturnsOne() = runTest {
        dao.insert(testEntities[0])

        val result = dao.delete(testEntities[0])

        assertEquals(1, result)
    }
}