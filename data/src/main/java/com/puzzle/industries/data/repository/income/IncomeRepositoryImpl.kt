package com.puzzle.industries.data.repository.income

import com.puzzle.industries.data.database.dao.income.IncomeDao
import com.puzzle.industries.data.mapper.income.IncomeMapper
import com.puzzle.industries.domain.models.income.Income
import com.puzzle.industries.domain.repository.income.IncomeRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class IncomeRepositoryImpl @Inject constructor(
    private val incomeMapper: IncomeMapper,
    private val incomeDao: IncomeDao
) : IncomeRepository {

    override suspend fun create(vararg entity: Income): Boolean {
        val entities = entity.map { income -> incomeMapper.toIncomeEntity(income = income) }
        val result = incomeDao.insert(entity = entities)
        return result.size == entity.size
    }

    override fun read(): Flow<List<Income>> {
        return incomeDao.read().flowOn(context = Dispatchers.IO).map {
            val entities = mutableListOf<Income>()
            it.forEach { incomeEntity ->
                entities.add(incomeMapper.toIncome(income = incomeEntity))
            }
            return@map entities
        }.conflate()
    }

    override suspend fun update(entity: Income): Boolean {
        val updateEntity = incomeMapper.toIncomeEntity(income = entity)
        val result = incomeDao.update(updateEntity)
        return result == 1
    }

    override suspend fun delete(vararg entity: Income): Boolean {
        val entities = entity.map { income -> incomeMapper.toIncomeEntity(income = income) }
        val result = incomeDao.delete(entity = entities)
        return result == entity.size
    }
}