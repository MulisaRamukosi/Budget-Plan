package com.puzzle.industries.data.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.domain.common.crud.Insert
import com.puzzle.industries.domain.common.crud.Update
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal abstract class BaseRepoUpdateTest<Repo, E>(val testEntities: Array<E>) :
    BaseRepoTest<Repo>() where Repo : Update<E>, Repo : Insert<E> {

    @Test
    fun updateEntity_DbHasNoEntities_ReturnsFalse() = runTest {
        val result = repo.update(*testEntities)

        assertFalse(result.response)
        assertEquals("Failed to update item.", result.message)
    }

    @Test
    fun updateEntity_DbHasEntities_ReturnsTrue() = runTest {
        repo.insert(*testEntities)

        val result = repo.update(*testEntities)

        assertTrue(result.response)
        assertEquals("Successfully updated item.", result.message)
    }

    @Test
    fun updateEntity_SomeEntitiesExistInDb_ReturnsTrue() = runTest {
        repo.insert(*testEntities.copyOfRange(0, 1))

        val result = repo.update(*testEntities)

        assertTrue(result.response)
        assertEquals(
            "Some items were not updated because they don\'t exist in the database.",
            result.message
        )
    }
}