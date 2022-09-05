package com.puzzle.industries.data.repository.income

import com.puzzle.industries.data.database.dao.income.IncomeHistoryDao
import com.puzzle.industries.data.mapper.income.IncomeHistoryMapper
import com.puzzle.industries.data.util.ResponseMessageFactory
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.income.IncomeHistory
import com.puzzle.industries.domain.repository.income.IncomeHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class IncomeHistoryRepositoryImpl constructor(
    private val incomeHistoryMapper: IncomeHistoryMapper,
    private val incomeHistoryDao: IncomeHistoryDao,
    private val responseMessageFactory: ResponseMessageFactory
) : IncomeHistoryRepository {

    override suspend fun insert(vararg entity: IncomeHistory): Response<Boolean> {
        val entities =
            entity.map { incomeHistory -> incomeHistoryMapper.toIncomeHistoryEntity(incomeHistory = incomeHistory) }
                .toTypedArray()

        return responseMessageFactory.buildInsertMessage {
            val result = incomeHistoryDao.insert(entity = entities)
            result.size == entity.size
        }
    }

    override fun read(): Response<Flow<List<IncomeHistory>>> {
        return Response(
            response = incomeHistoryDao.read().flowOn(context = Dispatchers.IO).map {
                val entities = mutableListOf<IncomeHistory>()
                it.forEach { incomeHistoryEntity ->
                    entities.add(incomeHistoryMapper.toIncomeHistory(incomeHistory = incomeHistoryEntity))
                }
                return@map entities
            }.conflate()
        )
    }

    override suspend fun delete(vararg entity: IncomeHistory): Response<Boolean> {
        val entities =
            entity.map { incomeHistory -> incomeHistoryMapper.toIncomeHistoryEntity(incomeHistory = incomeHistory) }
                .toTypedArray()

        return responseMessageFactory.buildDeleteMessage(expectedAffectedRow = entities.size) {
            incomeHistoryDao.delete(entity = entities)
        }
    }
}