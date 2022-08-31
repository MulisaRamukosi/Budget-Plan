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
internal abstract class BaseDaoUpdateTest<Dao, E>(val testEntities: List<E>) :
    BaseDaoTest<Dao>() where Dao : Update<E>, Dao : Insert<E>{

    @Test
    fun updateEntity_EntityDoesNotExistInDb_ReturnsZero() = runTest {
        val result = dao.update(testEntities[0])

        assertEquals(0, result)
    }

    @Test
    fun updateEntity_EntityExistInDb_ReturnsOne() = runTest {
        dao.insert(listOf(testEntities[0]))

        val result = dao.update(testEntities[0])

        assertEquals(1, result)
    }
}