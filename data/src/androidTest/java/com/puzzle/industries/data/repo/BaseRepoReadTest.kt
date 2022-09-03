package com.puzzle.industries.data.repo

import androidx.test.ext.junit.runners.AndroidJUnit4
import com.puzzle.industries.domain.common.crud.Insert
import com.puzzle.industries.domain.common.crud.Read
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runTest
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
internal abstract class BaseRepoReadTest<Repo, E>(val testEntities: Array<E>) :
    BaseRepoTest<Repo>() where Repo : Read<E>, Repo : Insert<E> {

    @Test
    fun readEntities_DbHasNoEntities_ReturnsEmptyFlow() = runTest {
        val result = repo.read()

        assertTrue(result.response.first().isEmpty())
    }

    @Test
    fun readEntities_DbHasEntities_ReturnsFlowWithEntities() = runTest {
        repo.insert(*testEntities)

        val result = repo.read()

        assertEquals(testEntities.size, result.response.first().size)
    }
}