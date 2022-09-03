package com.puzzle.industries.data.repository.income

import com.puzzle.industries.data.database.dao.income.IncomeDao
import com.puzzle.industries.data.mapper.income.IncomeMapper
import com.puzzle.industries.data.util.ResponseMessageFactory
import com.puzzle.industries.domain.common.response.Response
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
    private val incomeDao: IncomeDao,
    private val responseMessageFactory: ResponseMessageFactory
) : IncomeRepository {

    override suspend fun create(vararg entity: Income): Response<Boolean> {
        val entities =
            entity.map { income -> incomeMapper.toIncomeEntity(income = income) }.toTypedArray()

        return responseMessageFactory.buildInsertMessage {
            val result = incomeDao.insert(entity = entities)
            result.size == entity.size
        }
    }

    override fun read(): Response<Flow<List<Income>>> {
        return Response(
            response = incomeDao.read().flowOn(context = Dispatchers.IO).map {
                val entities = mutableListOf<Income>()
                it.forEach { incomeEntity ->
                    entities.add(incomeMapper.toIncome(income = incomeEntity))
                }
                return@map entities
            }.conflate()
        )
    }

    override suspend fun update(entity: Income): Response<Boolean> {
        val updateEntity = incomeMapper.toIncomeEntity(income = entity)

        return responseMessageFactory.buildUpdateMessage {
            val result = incomeDao.update(updateEntity)
            result == 1
        }
    }

    override suspend fun delete(vararg entity: Income): Response<Boolean> {
        val entities =
            entity.map { income -> incomeMapper.toIncomeEntity(income = income) }.toTypedArray()
        val result = incomeDao.delete(entity = entities)

        return responseMessageFactory.buildDeleteMessage(success = result == entity.size)
    }
}