package com.puzzle.industries.data.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.domain.common.crud.Insert
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runTest
import org.junit.Assert.*
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal abstract class BaseRepoInsertTest<Repo : Insert<E>, E>(val testEntities: Array<E>) :
    BaseRepoTest<Repo>() {

        @Test
        fun insertEntity_DbHasNoEntity_ReturnsTrue() = runTest {
            val result = repo.insert(*testEntities)

            assertTrue(result.response)
            assertEquals("Successfully saved item.", result.message)
        }

        @Test
        fun insertEntity_EntityAlreadyExistInDb_ReturnsFalse() = runTest {
            repo.insert(*testEntities)
            val result = repo.insert(*testEntities)

            assertFalse(result.response)
            assertNotNull(result.exception)
            assertEquals("Failed to save item because it already exist.", result.message)
        }
}