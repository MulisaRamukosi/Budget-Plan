package com.puzzle.industries.data.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.domain.common.crud.*
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal abstract class BaseRepoDeleteTest<Repo, E>(val testEntities: Array<E>) :
    BaseRepoTest<Repo>() where Repo : Delete<E>, Repo : Insert<E> {

    @Test
    fun deleteEntities_DbHasNoEntities_ReturnsFalse() = runTest {
        val result = repo.delete(*testEntities)

        assertFalse(result.response)
        assertEquals("Failed to delete item(s) because they don\'t exist.", result.message)
    }

    @Test
    fun deleteEntities_DbHasEntities_ReturnsTrue() = runTest {
        repo.insert(*testEntities)

        val result = repo.delete(*testEntities)

        assertTrue(result.response)
        assertEquals("Successfully deleted item(s).", result.message)
    }

    @Test
    fun deleteEntities_SomeEntitiesExistInDb_ReturnsTrue() = runTest {
        repo.insert(*testEntities.copyOfRange(0, 1))

        val result = repo.delete(*testEntities)

        assertTrue(result.response)
        assertEquals(
            "Some items were not deleted because they don\'t exist in the database.",
            result.message
        )
    }
}