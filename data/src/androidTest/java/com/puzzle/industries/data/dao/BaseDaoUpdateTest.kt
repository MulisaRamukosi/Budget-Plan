package com.puzzle.industries.data.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.database.dao.base.Insert
import com.puzzle.industries.data.database.dao.base.Update
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal abstract class BaseDaoUpdateTest<Dao, E>(val testEntities: Array<E>) :
    BaseDaoTest<Dao>() where Dao : Update<E>, Dao : Insert<E>{

    @Test
    fun updateEntity_EntityDoesNotExistInDb_ReturnsZero() = runTest {
        val result = dao.update(*testEntities.copyOfRange(0, 1))

        assertEquals(0, result)
    }

    @Test
    fun updateEntity_EntityExistInDb_ReturnsOne() = runTest {
        dao.insert(*testEntities.copyOfRange(0, 1))

        val result = dao.update(*testEntities.copyOfRange(0, 1))

        assertEquals(1, result)
    }
}