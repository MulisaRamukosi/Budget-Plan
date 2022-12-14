package com.puzzle.industries.data.dao

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.storage.database.dao.base.Insert
import com.puzzle.industries.data.storage.database.dao.base.Read
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal abstract class BaseDaoReadTest<Dao, E>(val testEntities: Array<E>) :
    BaseDaoTest<Dao>() where Dao : Read<E>, Dao : Insert<E> {

    @Test
    fun readAllEntities_DbEmpty_ReturnsEmptyList() = runTest {
        val entities = dao.read().first()

        assertTrue(entities.isEmpty())
    }

    @Test
    fun readAllEntities_DbHasEntities_ReturnsListOfAllEntities() = runTest {
        dao.insert(*testEntities)

        val entities = dao.read().first()

        assertEquals(testEntities.size, entities.size)
    }

}