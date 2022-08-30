package com.puzzle.industries.data.repository.expense

import com.puzzle.industries.data.database.dao.expense.ExpenseHistoryDao
import com.puzzle.industries.data.mapper.expense.ExpenseHistoryMapper
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
    private val expenseHistoryDao: ExpenseHistoryDao
) : ExpenseHistoryRepository {

    override suspend fun create(vararg entity: ExpenseHistory): Boolean {
        val entities =
            entity.map { expense -> expenseHistoryMapper.toExpenseHistoryEntity(expenseHistory = expense) }
        val result = expenseHistoryDao.insert(entity = entities)
        return result.size == entity.size
    }

    override suspend fun delete(vararg entity: ExpenseHistory): Boolean {
        val entities =
            entity.map { expense -> expenseHistoryMapper.toExpenseHistoryEntity(expenseHistory = expense) }
        val result = expenseHistoryDao.delete(entity = entities)
        return result == entity.size
    }

    override fun read(): Flow<List<ExpenseHistory>> {
        return expenseHistoryDao.read().flowOn(context = Dispatchers.IO).map {
            val entities = mutableListOf<ExpenseHistory>()
            it.forEach { expenseHistoryEntity ->
                entities.add(expenseHistoryMapper.toExpenseHistory(expenseHistoryEntity))
            }
            return@map entities
        }.conflate()
    }
}