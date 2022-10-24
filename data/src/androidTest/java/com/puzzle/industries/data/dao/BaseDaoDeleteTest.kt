package com.puzzle.industries.data.dao


import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.storage.database.dao.base.Delete
import com.puzzle.industries.data.storage.database.dao.base.Insert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal abstract class BaseDaoDeleteTest<Dao, E>(val testEntities: Array<E>) :
    BaseDaoTest<Dao>() where Dao : Delete<E>, Dao : Insert<E> {

    @Test
    fun deleteEntity_EntityDoesNotExistInDb_ReturnsZero() = runTest {
        val result = dao.delete(*testEntities.copyOfRange(0, 1))

        assertEquals(0, result)
    }

    @Test
    fun deleteEntity_EntityExistsInDb_ReturnsOne() = runTest {
        dao.insert(*testEntities)

        val result = dao.delete(*testEntities.copyOfRange(0, 1))

        assertEquals(1, result)
    }

    @Test
    fun deleteEntities_EntitiesDoNotExistInDb_ReturnsZero() = runTest {
        val result = dao.delete(*testEntities)

        assertEquals(0, result)
    }

    @Test
    fun deleteEntities_EntitiesExistInDb_ReturnsNumOfAffectedRows() = runTest {
        dao.insert(*testEntities)

        val result = dao.delete(*testEntities)

        assertEquals(2, result)
    }

    @Test
    fun deleteEntities_SomeEntitiesExistInDb_ReturnsNumOfAffectedRows() = runTest {
        dao.insert(*testEntities.copyOfRange(0, 1))

        val result = dao.delete(*testEntities)

        assertEquals(1, result)
    }
}