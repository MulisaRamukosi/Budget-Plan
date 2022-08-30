package com.puzzle.industries.data.repository.expenseGroup

import com.puzzle.industries.data.database.dao.expenseGroup.ExpenseGroupDao
import com.puzzle.industries.data.mapper.expenseGroup.ExpenseGroupMapper
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroup
import com.puzzle.industries.domain.models.expenseGroup.ExpenseGroupWithExpenses
import com.puzzle.industries.domain.repository.expenseGroup.ExpenseGroupRepository
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

internal class ExpenseGroupRepositoryImpl @Inject constructor(
    private val expenseGroupMapper: ExpenseGroupMapper,
    private val expenseGroupDao: ExpenseGroupDao
) : ExpenseGroupRepository {

    override suspend fun create(vararg entity: ExpenseGroup): Boolean {
        val entities =
            entity.map { expenseGroup -> expenseGroupMapper.toExpenseGroupEntity(expenseGroup = expenseGroup) }
        val result = expenseGroupDao.insert(entity = entities)
        return result.size == entity.size
    }

    override fun read(): Flow<List<ExpenseGroupWithExpenses>> {
        return expenseGroupDao.read().flowOn(context = Dispatchers.IO).map {
            val entities = mutableListOf<ExpenseGroupWithExpenses>()
            it.forEach { expenseGroupWithExpensesEntity ->
                entities.add(expenseGroupMapper.toExpenseGroupWithExpenses(entity = expenseGroupWithExpensesEntity))
            }
            return@map entities
        }.conflate()
    }

    override suspend fun update(entity: ExpenseGroup): Boolean {
        val updateEntity = expenseGroupMapper.toExpenseGroupEntity(expenseGroup = entity)
        val result = expenseGroupDao.update(updateEntity)
        return result == 1
    }

    override suspend fun delete(vararg entity: ExpenseGroup): Boolean {
        val entities =
            entity.map { expenseGroup -> expenseGroupMapper.toExpenseGroupEntity(expenseGroup = expenseGroup) }
        val result = expenseGroupDao.delete(entity = entities)
        return result == entity.size
    }
}