package com.puzzle.industries.data.repository.expense

import com.puzzle.industries.data.database.dao.expense.ExpenseHistoryDao
import com.puzzle.industries.data.mapper.expense.ExpenseHistoryMapper
import com.puzzle.industries.data.util.ResponseMessageFactory
import com.puzzle.industries.domain.common.response.Response
import com.puzzle.industries.domain.models.expense.ExpenseHistory
import com.puzzle.industries.domain.repository.expense.ExpenseHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ExpenseHistoryRepositoryImpl @Inject constructor(
    private val expenseHistoryMapper: ExpenseHistoryMapper,
    private val expenseHistoryDao: ExpenseHistoryDao,
    private val responseMessageFactory: ResponseMessageFactory
) : ExpenseHistoryRepository {

    override suspend fun insert(vararg entity: ExpenseHistory): Response<Boolean> {
        val entities =
            entity.map { expense -> expenseHistoryMapper.toExpenseHistoryEntity(expenseHistory = expense) }
                .toTypedArray()


        return responseMessageFactory.buildInsertMessage {
            val result = expenseHistoryDao.insert(entity = entities)
            result.size == entity.size
        }
    }

    override suspend fun delete(vararg entity: ExpenseHistory): Response<Boolean> {
        val entities =
            entity.map { expense -> expenseHistoryMapper.toExpenseHistoryEntity(expenseHistory = expense) }
                .toTypedArray()

        return responseMessageFactory.buildDeleteMessage(expectedAffectedRow = entities.size) {
            expenseHistoryDao.delete(entity = entities)
        }
    }

    override fun read(): Response<Flow<List<ExpenseHistory>>> {
        return Response(
            response = expenseHistoryDao.read().flowOn(context = Dispatchers.IO).map {
                val entities = mutableListOf<ExpenseHistory>()
                it.forEach { expenseHistoryEntity ->
                    entities.add(expenseHistoryMapper.toExpenseHistory(expenseHistoryEntity))
                }
                return@map entities
            }.conflate()
        )
    }
}