package com.puzzle.industries.data

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.data.database.dao.base.Insert
import com.puzzle.industries.data.database.dao.base.ReadAll
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
abstract class BaseReadAllTest<Dao, E>(val testEntities: List<E>) :
    BaseDaoTest<Dao>() where Dao : ReadAll<E>, Dao : Insert<E> {

    @Test
    fun readAllEntities_DbEmpty_ReturnsEmptyList() = runTest {
        val entities = dao.readAll().first()

        assertTrue(entities.isEmpty())
    }

    @Test
    fun readAllEntities_DbHasEntities_ReturnsListOfAllEntities() = runTest {
        testEntities.forEach { dao.insert(it) }

        val entities = dao.readAll().first()

        assertEquals(testEntities.size, entities.size)
    }

}