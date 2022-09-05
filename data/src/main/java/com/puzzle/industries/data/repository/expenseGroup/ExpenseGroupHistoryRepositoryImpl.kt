package com.puzzle.industries.data.repository.expenseGroup

import com.puzzle.industries.data.database.dao.expenseGroup.ExpenseGroupHistoryDao
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupHistoryMapper
import com.puzzle.industries.data.util.ResponseMessageFactory
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupHistory
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map

internal class ExpenseGroupHistoryRepositoryImpl constructor(
    private val expenseGroupHistoryMapper: ExpenseGroupHistoryMapper,
    private val expenseGroupHistoryDao: ExpenseGroupHistoryDao,
    private val responseMessageFactory: ResponseMessageFactory
) : ExpenseGroupHistoryRepository {

    override suspend fun insert(vararg entity: ExpenseGroupHistory): Response<Boolean> {
        val entities = entity.map { expenseGroupHistory ->
            expenseGroupHistoryMapper.toExpenseGroupHistoryEntity(expenseGroupHistory = expenseGroupHistory)
        }.toTypedArray()

        return responseMessageFactory.buildInsertMessage {
            val result = expenseGroupHistoryDao.insert(entity = entities)
            result.size == entity.size
        }
    }

    override fun read(): Response<Flow<List<ExpenseGroupHistory>>> {
        return Response(
            response = expenseGroupHistoryDao.read().flowOn(context = Dispatchers.IO).map {
                val entities = mutableListOf<ExpenseGroupHistory>()
                it.forEach { expenseGroupHistoryEntity ->
                    entities.add(expenseGroupHistoryMapper.toExpenseGroupHistory(expenseGroupHistory = expenseGroupHistoryEntity))
                }
                return@map entities
            }.conflate()
        )
    }

    override suspend fun delete(vararg entity: ExpenseGroupHistory): Response<Boolean> {
        val entities = entity.map { expenseGroupHistory ->
            expenseGroupHistoryMapper.toExpenseGroupHistoryEntity(expenseGroupHistory = expenseGroupHistory)
        }.toTypedArray()

        return responseMessageFactory.buildDeleteMessage(expectedAffectedRow = entities.size) {
            expenseGroupHistoryDao.delete(entity = entities)
        }
    }
}