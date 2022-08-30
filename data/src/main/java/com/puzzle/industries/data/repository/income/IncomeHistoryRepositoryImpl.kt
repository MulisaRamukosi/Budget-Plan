package com.puzzle.industries.data.repository.income

import com.puzzle.industries.data.database.dao.income.IncomeHistoryDao
import com.puzzle.industries.data.mapper.income.IncomeHistoryMapper
import com.puzzle.industries.domain.models.income.IncomeHistory
import com.puzzle.industries.domain.repository.income.IncomeHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class IncomeHistoryRepositoryImpl @Inject constructor(
    private val incomeHistoryMapper: IncomeHistoryMapper,
    private val incomeHistoryDao: IncomeHistoryDao
) : IncomeHistoryRepository {

    override suspend fun create(vararg entity: IncomeHistory): Boolean {
        val entities =
            entity.map { incomeHistory -> incomeHistoryMapper.toIncomeHistoryEntity(incomeHistory = incomeHistory) }
        val result = incomeHistoryDao.insert(entity = entities)
        return result.size == entity.size
    }

    override fun read(): Flow<List<IncomeHistory>> {
        return incomeHistoryDao.read().flowOn(context = Dispatchers.IO).map {
            val entities = mutableListOf<IncomeHistory>()
            it.forEach { incomeHistoryEntity ->
                entities.add(incomeHistoryMapper.toIncomeHistory(incomeHistory = incomeHistoryEntity))
            }
            return@map entities
        }.conflate()
    }

    override suspend fun delete(vararg entity: IncomeHistory): Boolean {
        val entities =
            entity.map { incomeHistory -> incomeHistoryMapper.toIncomeHistoryEntity(incomeHistory = incomeHistory) }
        val result = incomeHistoryDao.delete(entity = entities)
        return result == entity.size
    }
}