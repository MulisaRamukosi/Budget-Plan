package com.puzzle.industries.data.repository.expenseGroup

import com.puzzle.industries.data.database.dao.expenseGroup.ExpenseGroupHistoryDao
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupHistoryMapper
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupHistory
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupHistoryRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ExpenseGroupHistoryRepositoryImpl @Inject constructor(
    private val expenseGroupHistoryMapper: ExpenseGroupHistoryMapper,
    private val expenseGroupHistoryDao: ExpenseGroupHistoryDao
) : ExpenseGroupHistoryRepository {

    override suspend fun create(vararg entity: ExpenseGroupHistory): Boolean {
        val entities = entity.map { expenseGroupHistory ->
            expenseGroupHistoryMapper.toExpenseGroupHistoryEntity(expenseGroupHistory = expenseGroupHistory)
        }
        val result = expenseGroupHistoryDao.insert(entity = entities)
        return result.size == entity.size
    }

    override fun read(): Flow<List<ExpenseGroupHistory>> {
        return expenseGroupHistoryDao.read().flowOn(context = Dispatchers.IO).map {
            val entities = mutableListOf<ExpenseGroupHistory>()
            it.forEach { expenseGroupHistoryEntity ->
                entities.add(expenseGroupHistoryMapper.toExpenseGroupHistory(expenseGroupHistory = expenseGroupHistoryEntity))
            }
            return@map entities
        }.conflate()
    }

    override suspend fun delete(vararg entity: ExpenseGroupHistory): Boolean {
        val entities = entity.map { expenseGroupHistory ->
            expenseGroupHistoryMapper.toExpenseGroupHistoryEntity(expenseGroupHistory = expenseGroupHistory)
        }
        val result = expenseGroupHistoryDao.delete(entity = entities)
        return result == entity.size
    }
}